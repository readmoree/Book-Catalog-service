package com.readmoree.service;

import java.util.List;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.ReviewRequestDto;
import com.readmoree.dtos.ReviewResponseDto;

public interface ReviewService {

	ApiResponse addreview(Integer customerId,Long bookId, ReviewRequestDto reviewRequestDto);

	ApiResponse deleteReview(Long bookId, Long reviewId);

	ApiResponse updateReview(Long bookId, Long reviewId, ReviewRequestDto reviewRequestDto);

	List<ReviewResponseDto> getAllReviewsByCustomer(Integer customerId);

	List<ReviewResponseDto> getAllReviewsOnBookByCustomer(Integer customerId,Long bookId);

	List<ReviewResponseDto> getAllReviewsOnBook(Long bookId);

}
