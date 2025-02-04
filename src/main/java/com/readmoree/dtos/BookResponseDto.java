package com.readmoree.dtos;

import java.time.LocalDate;

import com.readmoree.entities.Author;
import com.readmoree.entities.Binding;
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
	
<<<<<<< HEAD
	public BookResponseDto(Long bookId,String imgUrl, String isbn,  String title, Author author, Publisher publisher, double price, Language language, double discount) {
		super.setId(bookId);
=======
	public BookResponseDto(String imgUrl, String isbn,  String title, Author author, Publisher publisher, double price, Language language, double discount) {
>>>>>>> 4758a2c2c5e56d14167a243d44e7b2555ecf1969
		this.image = imgUrl;
		this.isbn=isbn;
		this.title=title;
		this.author=(Author)author;
		this.publisher=(Publisher)publisher;
		this.price=price;
		this.language=(Language)language;
		this.discount=discount;
		
	}

	

	
}
