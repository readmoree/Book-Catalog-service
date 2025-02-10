package com.readmoree.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.AuthorDao;
import com.readmoree.dao.BookDao;
import com.readmoree.dao.PublisherDao;
import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookCustomResponseDto;
import com.readmoree.dtos.BookFilterRequestDto;
import com.readmoree.dtos.BookFilterResponseDTO;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.dtos.BookUpdateDto;
import com.readmoree.entities.Author;
import com.readmoree.entities.Book;
import com.readmoree.entities.BooksMapping;
import com.readmoree.entities.BooksMappingId;
import com.readmoree.entities.Labels;
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
	public ApiResponse addBook(BookRequestDto bookDto) {
		
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
	}

	@Override
	public ApiResponse updateBook(Long bookId, BookUpdateDto bookDto) {

		//validate bookId
		Book bookToBeUpdated = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Invalid book id")); 

		bookToBeUpdated.setPrice(bookDto.getPrice());
		bookToBeUpdated.setDiscount(bookDto.getDiscount());
		bookToBeUpdated.setTotalAvailableCount(bookDto.getTotalAvailableCount());

		modelMapper.map(bookToBeUpdated, BookUpdateDto.class);
		
		return new ApiResponse("Book details updated successfully!");
	}

	@Override
	public BookFilterResponseDTO searchBooks(String searchKeyword) {

		List<Book> bookList = bookDao.searchBooks(searchKeyword);
		List<BookResponseDto> bookResponseDtoList = bookList.stream()
					.map(bookResponseDto->modelMapper.map(bookResponseDto, BookResponseDto.class))
					.collect(Collectors.toList());
		
		List<String> authors = bookList.stream()
				.map(book -> book.getAuthor() != null
				? book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()
						: null) // Return null so it can be filtered out
				.filter(Objects::nonNull) // Remove null values
				.distinct()
				.collect(Collectors.toList());
		List<String> publishers = bookList.stream()
				.map(book -> Optional.ofNullable(book.getPublisher())
						.map(Publisher::getName)
						.orElse(null)) // Return null for filtering
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());

		List<String> languages = bookList.stream()
				.map(book -> Optional.ofNullable(book.getLanguage())
						.map(lang -> lang.name())
						.orElse(null)) // Return null for filtering
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());
		
		return new BookFilterResponseDTO(bookResponseDtoList, authors, publishers, languages);
	}

	@Override
	public boolean deleteBookById(Long bookId) {
		//check if id is valid
		Book bookTobeDeleted = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Invalid book id"));

		if(bookTobeDeleted.isAvailable()) {
			bookTobeDeleted.setAvailable(false);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<BookResponseDto> getAllBooks() {
		List<BookResponseDto> allBooksWithMappings = bookDao.findAllBooksWithMappings();
		return allBooksWithMappings;
	}

	@Override
	public BookFilterResponseDTO filterBooks(BookFilterRequestDto filterRequest) {
		System.out.println("IN THE filterBook");
		List<Book> books = bookDao.filterBooks(
				filterRequest.getLabels(),
				filterRequest.getCategory(),
				filterRequest.getSubCategory());

		System.out.println("books"+ books);

		// Convert books to DTO format
		List<BookResponseDto> bookList = books.stream()
				.map(book -> new BookResponseDto(
						book.getId(),
						book.getImage(),
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
				.map(book -> book.getAuthor() != null
				? book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()
						: null) // Return null so it can be filtered out
				.filter(Objects::nonNull) // Remove null values
				.distinct()
				.collect(Collectors.toList());

		List<String> publishers = books.stream()
				.map(book -> Optional.ofNullable(book.getPublisher())
						.map(Publisher::getName)
						.orElse(null)) // Return null for filtering
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());

		List<String> languages = books.stream()
				.map(book -> Optional.ofNullable(book.getLanguage())
						.map(lang -> lang.name())
						.orElse(null)) // Return null for filtering
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());

		return BookFilterResponseDTO.builder()
				.books(bookList)
				.authors(authors.isEmpty() ? Collections.emptyList() : authors)
				.publishers(publishers.isEmpty() ? Collections.emptyList() : publishers)
				.languages(languages.isEmpty() ? Collections.emptyList() : languages)
				.build();
	}

	@Override
	public BookResponseDto getBookById(Long bookId) {
		Book book = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Invalid book id"));
		return modelMapper.map(book, BookResponseDto.class);
	}

	@Override
	public List<BookResponseDto> getBookListByIdArray(List<Long> bookIds) {
		List<Book> bookList = new ArrayList<>();
		for(Long id: bookIds) {
			Book book = bookDao.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid book id"));
			bookList.add(book);
		}
		return bookList.stream()
				.map(book->modelMapper.map(book, BookResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookCustomResponseDto getCustomBookDetailsById(Long bookId) {
		Book book = bookDao.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Invalid book id"));
		BookCustomResponseDto customBookResponseDto = new BookCustomResponseDto();
		customBookResponseDto.setId(bookId);
		customBookResponseDto.setCreatedOn(book.getCreatedOn());
		customBookResponseDto.setUpdatedOn(book.getUpdatedOn());
		customBookResponseDto.setImage(book.getImage());
		customBookResponseDto.setIsbn(book.getIsbn());
		customBookResponseDto.setTitle(book.getTitle());
		return customBookResponseDto;
	}

	@Override
	public List<Labels> getAllLabels() {	
		return booksMappingDao.findDistinctLabels();
	}

	@Override
	public List<String> getAllCategoriesByLabel(Labels label) {
		return booksMappingDao.findDistinctCategoryByLabel(label);
	}

	@Override
	public List<String> getAllSubCategoriesByCategoryAndLabel(Labels label, String category) {
		return booksMappingDao.findDistinctSubCategoryByLabelAndCategory(label, category);
	}

}
