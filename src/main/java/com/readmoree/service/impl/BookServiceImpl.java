package com.readmoree.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.AuthorDao;
import com.readmoree.dao.BookDao;
import com.readmoree.dao.PublisherDao;
import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookFilterRequestDto;
import com.readmoree.dtos.BookFilterResponseDTO;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.entities.Author;
import com.readmoree.entities.Book;
import com.readmoree.entities.BooksMapping;
import com.readmoree.entities.BooksMappingId;
import com.readmoree.entities.Publisher;
import com.readmoree.exception.ResourceNotFoundException;
import com.readmoree.service.BookService;
import com.readmoree.dao.BooksMappingDao;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

	private BookDao bookDao;

	private ModelMapper modelMapper;

	private AuthorDao authorDao;

	private PublisherDao publisherDao;

	private BooksMappingDao booksMappingDao;

	@Override
	public ApiResponse addBook(Long userId, BookRequestDto bookDto) {
		//validate if userId is of admin
		//call user service for the same!
		//if userId is of admin: add a product
		//else response msg
		if(userId==1) {//suppose 1 belongs to admin
			//convert dto to entity
			Book book = modelMapper.map(bookDto, Book.class);
			Author author = authorDao.findById(bookDto.getAuthorId()).orElseThrow(()->new ResourceNotFoundException("invalid author id"));
			book.setAuthor(author);
			
			Publisher publisher = publisherDao.findById(bookDto.getPublisherId()).orElseThrow(()->new ResourceNotFoundException("invalid publisher id"));
			book.setPublisher(publisher);
			Book addedBook = bookDao.save(book);

			// Create and save book mappings

			List<BooksMapping> mappings = bookDto.getBookMappings().stream()
				    .map(mappingRequest -> {
				        BooksMapping mapping = new BooksMapping();
				        mapping.setId(new BooksMappingId(
				            addedBook.getId(),
				            mappingRequest.getLabels(),
				            mappingRequest.getCategory(),
				            mappingRequest.getSubCategory()
				        ));
				        mapping.setBook(addedBook);
				        return mapping;
				    })
				    .collect(Collectors.toList());

			booksMappingDao.saveAll(mappings);

			//convert to dto
			BookResponseDto bookRequestDto = modelMapper.map(addedBook, BookResponseDto.class);
			return new ApiResponse("Book added with id:"+addedBook.getId() +"successfully",bookRequestDto);

		}else {
			return new ApiResponse("Only admin can add product");
		}
	}

	@Override
	public ApiResponse updateBook(Long userId, Long bookId, BookRequestDto bookDto) {
		//validate user(suppose userId=1 is admin
		if(userId==1) {
			//validate bookId
			Book bookToBeUpdated = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Invalid book id")); 

			//get author from dto
			Author author = authorDao.findById(bookDto.getAuthorId()).orElseThrow(()->new ResourceNotFoundException("Invalid author id"));

			bookToBeUpdated.setAuthor(author); 

			//get publisher from dto
			Publisher publisher = publisherDao.findById(bookDto.getPublisherId()).orElseThrow(()->new ResourceNotFoundException("Invalid publisher id"));

			bookToBeUpdated.setPublisher(publisher);
			bookToBeUpdated.setIsbn(bookDto.getIsbn());
			bookToBeUpdated.setTitle(bookDto.getTitle());
			bookToBeUpdated.setPrice(bookDto.getPrice());
			bookToBeUpdated.setPublicationDate(bookDto.getPublicationDate());
			bookToBeUpdated.setPageCount(bookDto.getPageCount());
			bookToBeUpdated.setDescription(bookDto.getDescription());
			bookToBeUpdated.setLanguage(bookDto.getLanguage());
			bookToBeUpdated.setBinding(bookDto.getBinding());
			bookToBeUpdated.setAvailable(bookDto.isAvailable());

			//map to responsedto
			BookResponseDto updatedBook = modelMapper.map(bookToBeUpdated, BookResponseDto.class);
			return new ApiResponse("Book details updated successfully", updatedBook);
		}
		else {

			return new ApiResponse("Error occurred! try again later!");
		}
	}

	@Override
	public List<BookResponseDto> searchBooks(String title, String description,String isbn, String firstName, String lastName) {
		// TODO Auto-generated method stub
		System.out.println("🔍 Searching books with: title=" + title + ", description=" + description +
				", isbn=" + isbn + ", firstName=" + firstName + ", lastName=" + lastName);

		List<Book> bookList = bookDao.searchBooks(title, description, isbn, firstName, lastName);
		return  bookList.stream()
				.map(book -> 
				modelMapper.map(book, BookResponseDto.class))
				.collect(Collectors.toList());	
	}

	@Override
	public boolean deleteBookById(Long id) {
		//check if id is valid
		Book bookTobeDeleted = bookDao.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid book id"));

		if(bookTobeDeleted.isAvailable()) {
			bookTobeDeleted.setAvailable(false);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<BookResponseDto> getAllBooks() {
		List<Book> allBooks = bookDao.findAll();

		return allBooks.stream()
				.map(book -> modelMapper.map(book, BookResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookFilterResponseDTO filterBooks(BookFilterRequestDto filterRequest) {
		List<Book> books =  bookDao.filterBooks(
                filterRequest.getLabels(),
                filterRequest.getCategory(),
                filterRequest.getSubCategory());
		 
		// Convert books to DTO format
		List<BookResponseDto> bookList = books.stream()
			    .map(book -> new BookResponseDto(
			        book.getIsbn(),
			        book.getTitle(),
			        book.getAuthor(),
			        book.getPublisher(),
			        book.getPrice(),
			        book.getLanguage(),
			        book.getDiscount()
			    ))
			    .collect(Collectors.toList());
	        
	     // Extract unique authors, publishers, and languages
	        List<String> authors = books.stream()
	                .map(book -> book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName())
	                .distinct()
	                .collect(Collectors.toList());

	        List<String> publishers = books.stream()
	                .map(book -> book.getPublisher().getName())
	                .distinct()
	                .collect(Collectors.toList());

	        List<String> languages = books.stream()
	                .map(book -> book.getLanguage().name())  // Convert Enum to String
	                .distinct()
	                .collect(Collectors.toList());

	        return BookFilterResponseDTO.builder()
	                .books(bookList)
	                .authors(authors)
	                .publishers(publishers)
	                .languages(languages)
	                .build();
	}

}
