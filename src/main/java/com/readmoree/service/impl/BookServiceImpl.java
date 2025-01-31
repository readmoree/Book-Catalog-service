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
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.entities.Author;
import com.readmoree.entities.Book;
import com.readmoree.entities.Publisher;
import com.readmoree.exception.ResourceNotFoundException;
import com.readmoree.service.BookService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

	private BookDao bookDao;

	private ModelMapper modelMapper;

	private AuthorDao authorDao;

	private PublisherDao publisherDao;

	@Override
	public ApiResponse addBook(Long userId, BookRequestDto bookDto) {
		//validate if userId is of admin
		//call user service for the same!
		//if userId is of admin: add a product
		//else response msg
		if(userId==1) {//suppose 1 belongs to admin
			//convert dto to entity
			Book book = modelMapper.map(bookDto, Book.class);
			Book addedBook = bookDao.save(book);

			//convert to dto
			BookResponseDto bookRequestDto = modelMapper.map(addedBook, BookResponseDto.class);
			return new ApiResponse("Book added with id:"+addedBook.getId() +"successfully",bookRequestDto);

		}else {
			return new ApiResponse("Only admin can add product");
		}
	}

	@Override
	public ApiResponse updateBookPrice(Long userId, Long bookId, BookRequestDto bookDto) {
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
			return new ApiResponse("Book price updated successfully", updatedBook);
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

}
