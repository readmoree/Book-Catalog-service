package com.readmoree.service;

import java.util.List;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookFilterRequestDto;
import com.readmoree.dtos.BookFilterResponseDTO;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;

public interface BookService {

	ApiResponse addBook(Integer userId, BookRequestDto bookDto);

	ApiResponse updateBook(Long userId, Long bookId, BookRequestDto bookDto);

//	List<BookResponseDto> findBook(String searchQuery);

	List<BookResponseDto> searchBooks(String title, String firstName, String lastName, String description, String isbn);

	boolean deleteBookById(Long id);

	List<BookResponseDto> getAllBooks();
	
	BookResponseDto getBookById(Long bookId);

	List<BookResponseDto> getBookListByIdArray(List<Long> bookIds);

	public BookFilterResponseDTO filterBooks(BookFilterRequestDto filterRequest);

}
