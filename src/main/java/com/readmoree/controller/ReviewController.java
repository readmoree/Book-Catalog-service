package com.readmoree.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://0.0.0.0:3000")
public class ReviewController {
	
	private ReviewService reviewService;
	
	private Integer getCustomerId() {
		// Retrieve the customerId from the authentication object in the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer customerId = (Integer) authentication.getPrincipal(); // customerId is set as the principal 
        return customerId;

	}
	
	//add a review
	@PostMapping("/customer/book/{bookId}")
	public ResponseEntity<?> addReview(@PathVariable Long bookId, 
									   @RequestBody ReviewRequestDto reviewRequestDto){
		Integer customerId = getCustomerId();
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addreview(customerId, bookId, reviewRequestDto));
	}
		
	//delete a review
	@DeleteMapping("/customer/book/{bookId}/review/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable Long bookId,
										  @PathVariable Long reviewId){
		
		return ResponseEntity.ok(reviewService.deleteReview(bookId, reviewId));
	}
	
	//update a review
	@PutMapping("/customer/{userId}/book/{bookId}/review/{reviewId}")
	public ResponseEntity<?> updateReview(@PathVariable Long bookId,
										  @PathVariable Long reviewId,
										  @RequestBody ReviewRequestDto reviewRequestDto){

		return ResponseEntity.ok(reviewService.updateReview(bookId, reviewId,reviewRequestDto));
	}
	
	//list all reviews of a particular customer
	@GetMapping("/customer")
	public ResponseEntity<?> getAllReviewsByCustomer(){
		List<ReviewResponseDto> reviewList = reviewService.getAllReviewsByCustomer(getCustomerId());
		return ResponseEntity.ok(reviewList);
	}
	
	//list all review of a customer on a particular book
	@GetMapping("/customer/book/{bookId}")
	public ResponseEntity<?> getAllReviewsOnBookByCustomer(@PathVariable Long bookId){
		List<ReviewResponseDto> reviewList = reviewService.getAllReviewsOnBookByCustomer(getCustomerId(),bookId);
		return ResponseEntity.ok(reviewList);
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<?> getAllReviewsOnBook(@PathVariable Long bookId){
		List<ReviewResponseDto> reviewList =reviewService.getAllReviewsOnBook(bookId);
		return ResponseEntity.ok(reviewList);
	}
	

}
