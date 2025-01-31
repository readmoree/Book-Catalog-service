package com.readmoree.service;

import java.util.List;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.ReviewRequestDto;
import com.readmoree.dtos.ReviewResponseDto;

public interface ReviewService {

	ApiResponse addreview(Long userId, Long bookId, ReviewRequestDto reviewRequestDto);

	ApiResponse deleteReview(Long userId, Long bookId, Long reviewId);

	ApiResponse updateReview(Long userId, Long bookId, Long reviewId, ReviewRequestDto reviewRequestDto);

	List<ReviewResponseDto> getAllReviewsByCustomer(Long userId);

	List<ReviewResponseDto> getAllReviewsOnBookByCustomer(Long userId, Long bookId);

}
