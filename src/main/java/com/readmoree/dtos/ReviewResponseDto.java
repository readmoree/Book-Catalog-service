package com.readmoree.dtos;

import com.readmoree.entities.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto extends BaseEntity {
	
	private Long customerId;
	
	private Book bookId;
	
	private String comment;
	
	private int rating;

}
