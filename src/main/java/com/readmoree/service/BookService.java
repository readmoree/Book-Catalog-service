package com.readmoree.service;

import java.util.List;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookCustomResponseDto;
import com.readmoree.dtos.BookFilterRequestDto;
import com.readmoree.dtos.BookFilterResponseDTO;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.dtos.BookUpdateDto;
import com.readmoree.entities.Labels;

public interface BookService {

	ApiResponse addBook(BookRequestDto bookDto);

	ApiResponse updateBook(Long bookId, BookUpdateDto bookDto);

	BookFilterResponseDTO searchBooks(String searchKeyword);

	boolean deleteBookById(Long bookId);

	List<BookResponseDto> getAllBooks();
	
	BookResponseDto getBookById(Long bookId);

	List<BookResponseDto> getBookListByIdArray(List<Long> bookIds);

	public BookFilterResponseDTO filterBooks(BookFilterRequestDto filterRequest);

	BookCustomResponseDto getCustomBookDetailsById(Long bookId);
	
	List<Labels> getAllLabels();
	
	List<String> getAllCategoriesByLabel(Labels label);
	
	List<String> getAllSubCategoriesByCategoryAndLabel(Labels label, String category);
}
