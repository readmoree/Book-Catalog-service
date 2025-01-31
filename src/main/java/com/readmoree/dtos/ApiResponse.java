package com.readmoree.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//for sending response from server to client
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	
	private LocalDateTime timeStamp;
	private String message;
	private BookResponseDto book;
	private ReviewResponseDto review;
	
	public ApiResponse(String message, BookResponseDto book) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
		this.book = book;
	}
	public ApiResponse(String message) {
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
	public ApiResponse(String message, BookResponseDto book,ReviewResponseDto review) {
		super();
		this.message = message;
		this.timeStamp=LocalDateTime.now();
		this.book = book;
		this.review=review;
	}
}