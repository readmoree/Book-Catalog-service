package com.readmoree.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="reviews")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Review extends BaseEntity {

	@Column(name="customer_id",unique = true)
	private Integer customerId;
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	private String comment;
	
	private int rating;
	
}
