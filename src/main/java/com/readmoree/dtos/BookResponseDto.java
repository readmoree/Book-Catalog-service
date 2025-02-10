package com.readmoree.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.readmoree.entities.Author;
import com.readmoree.entities.Binding;
import com.readmoree.entities.Labels;
import com.readmoree.entities.Language;
import com.readmoree.entities.Publisher;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto extends BaseEntity {

	private String image;

	private String isbn;

	private String title;

	private double price;

	private LocalDate publicationDate;

	private String description;

	private long pageCount;

	private Language language;

	private Binding binding;

	private Author author;

	private Publisher publisher;

	private double discount;

	private Long totalAvailableCount;

	private Labels lables;

	private String category;

	private String subcategory;

	public BookResponseDto(Long bookId,String imgUrl, String isbn,  String title, Author author, Publisher publisher, double price, Language language, double discount) {
		super.setId(bookId);
		this.image = imgUrl;
		this.isbn=isbn;
		this.title=title;
		this.author=(Author)author;
		this.publisher=(Publisher)publisher;
		this.price=price;
		this.language=(Language)language;
		this.discount=discount;

	}

	public BookResponseDto(Long bookId, String imgUrl, String isbn, String title, 
			Author author, Publisher publisher, double price, 
			Language language, double discount,Long totalAvailableCount,LocalDateTime createdOn, 
			LocalDateTime updatedOn, LocalDate publicationDate,String description, Binding binding,Labels labels, 
			String category, String subCategory) {
		super.setId(bookId);
		super.setCreatedOn(createdOn);
		super.setUpdatedOn(updatedOn);
		this.image = imgUrl;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.language = language;
		this.discount = discount;
		this.totalAvailableCount = totalAvailableCount;
		this.publicationDate = publicationDate;
		this.description = description;
		this.binding = binding;
		this.lables = labels;
		this.category = category;
		this.subcategory = subCategory;
	}


}
