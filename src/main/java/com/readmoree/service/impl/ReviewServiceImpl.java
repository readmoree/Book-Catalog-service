package com.readmoree.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readmoree.dao.BookDao;
import com.readmoree.dao.ReviewDao;
import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.dtos.ReviewRequestDto;
import com.readmoree.dtos.ReviewResponseDto;
import com.readmoree.entities.Book;
import com.readmoree.entities.Review;
import com.readmoree.exception.ResourceNotFoundException;
import com.readmoree.service.ReviewService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private BookDao bookDao;
	private ReviewDao reviewDao;
	private ModelMapper modelMappper;


	private Book validateBook(Long bookId) {
		return bookDao.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Invalid book id"));
	}

	@Override
	public ApiResponse addreview(Long userId, Long bookId, ReviewRequestDto reviewRequestDto) {

		//validate user by calling user-service

		//validate Book
		Book book = validateBook(bookId);

		//entity to dto
		BookResponseDto bookResponseDto = modelMappper.map(book, BookResponseDto.class);

		//convert reviewDto to entity
		Review review = modelMappper.map(reviewRequestDto, Review.class);
		review.setCustomerId(userId);
		review.setBookId(book);

		Review savedReview = reviewDao.save(review);

		ReviewResponseDto reviewResponseDto = modelMappper.map(savedReview, ReviewResponseDto.class);

		return new ApiResponse("Review added!", bookResponseDto, reviewResponseDto);
	}

	@Override
	public ApiResponse deleteReview(Long userId, Long bookId, Long reviewId) {

		//validate user

		//validate Book
		Book book = validateBook(bookId);

		//entity to dto
		BookResponseDto bookResponseDto = modelMappper.map(book, BookResponseDto.class);

		//validate review
		Review review = reviewDao.findById(reviewId).orElseThrow(()-> new ResourceNotFoundException("Invalid review id"));

		reviewDao.delete(review);
		
		ReviewResponseDto reviewResponseDto = modelMappper.map(review, ReviewResponseDto.class);

		return new ApiResponse("Review deleted!", bookResponseDto, reviewResponseDto);
	}

	@Override
	public ApiResponse updateReview(Long userId, Long bookId, Long reviewId, ReviewRequestDto reviewRequestDto) {

		//validate user

		//validate book
		Book book = validateBook(bookId);

		//entity to dto
		BookResponseDto bookResponseDto = modelMappper.map(book, BookResponseDto.class);

		//validate review
		Review review = reviewDao.findById(reviewId).orElseThrow(()-> new ResourceNotFoundException("Invalid review id"));

		review.setComment(reviewRequestDto.getComment());
		review.setRating(reviewRequestDto.getRating());

		ReviewResponseDto reviewResponseDto = modelMappper.map(review, ReviewResponseDto.class);

		return new ApiResponse("Review updated!", bookResponseDto, reviewResponseDto);
	}

	@Override
	public List<ReviewResponseDto> getAllReviewsByCustomer(Long userId) {
		//validate user
		
		List<Review> reviewsByCustomerId = reviewDao.findByCustomerId(userId);
		

		return reviewsByCustomerId.stream()
				.map(review->modelMappper.map(review, ReviewResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReviewResponseDto> getAllReviewsOnBookByCustomer(Long userId, Long bookId) {

		//validate user
		
		//validate book
		Book book = validateBook(bookId);
		
		List<Review> reviews = reviewDao.findByCustomerIdAndBookId(userId, book);
		
		return  reviews.stream()
				.map(review->modelMappper.map(review, ReviewResponseDto.class))
				.collect(Collectors.toList());
	}

}
