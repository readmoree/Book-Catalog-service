package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmoree.entities.Review;
import java.util.List;
import com.readmoree.entities.Book;



public interface ReviewDao extends JpaRepository<Review, Long> {
	
	List<Review> findByCustomerId(Long customerId);
	
	List<Review> findByCustomerIdAndBookId(Long customerId, Book bookId);

}
