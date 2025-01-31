package com.readmoree.service;

import java.util.List;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;

public interface BookService {

	ApiResponse addBook(Long userId, BookRequestDto bookDto);

	ApiResponse updateBookPrice(Long userId, Long bookId, BookRequestDto bookDto);

//	List<BookResponseDto> findBook(String searchQuery);

	List<BookResponseDto> searchBooks(String title, String firstName, String lastName, String description, String isbn);

	boolean deleteBookById(Long id);

	List<BookResponseDto> getAllBooks();

}
