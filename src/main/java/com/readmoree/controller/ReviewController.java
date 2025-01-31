package com.readmoree.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dtos.ReviewRequestDto;
import com.readmoree.dtos.ReviewResponseDto;
import com.readmoree.service.ReviewService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {
	
	private ReviewService reviewService;
	
	//add a review
	@PostMapping("/user/{userId}/book/{bookId}")
	public ResponseEntity<?> addReview(@PathVariable Long userId, 
									   @PathVariable Long bookId, 
									   @RequestBody ReviewRequestDto reviewRequestDto){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addreview(userId, bookId, reviewRequestDto));
	}
		
	//delete a review
	@DeleteMapping("/user/{userId}/book/{bookId}/review/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable Long userId,
										 @PathVariable Long bookId,
										 @PathVariable Long reviewId){
		
		return ResponseEntity.ok(reviewService.deleteReview(userId, bookId, reviewId));
	}
	
	//update a review
	@PutMapping("/user/{userId}/book/{bookId}/review/{reviewId}")
	public ResponseEntity<?> updateReview(@PathVariable Long userId,
										 @PathVariable Long bookId,
										 @PathVariable Long reviewId,
										 @RequestBody ReviewRequestDto reviewRequestDto){

		return ResponseEntity.ok(reviewService.updateReview(userId, bookId, reviewId,reviewRequestDto));
	}
	
	//list all reviews of a particular customer
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllReviewsByCustomer(@PathVariable Long userId){
		List<ReviewResponseDto> reviewList = reviewService.getAllReviewsByCustomer(userId);
		return ResponseEntity.ok(reviewList);
	}
	
	//list all review of a customer on a particular book
	@GetMapping("/user/{userId}/book/{bookId}")
	public ResponseEntity<?> getAllReviewsOnBookByCustomer(@PathVariable Long userId,
			 											   @PathVariable Long bookId){
		List<ReviewResponseDto> reviewList = reviewService.getAllReviewsOnBookByCustomer(userId, bookId);
		return ResponseEntity.ok(reviewList);
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<?> getAllReviewsOnBook(@PathVariable Long bookId){
		List<ReviewResponseDto> reviewList =reviewService.getAllReviewsOnBook(bookId);
		return ResponseEntity.ok(reviewList);
	}
	

}
