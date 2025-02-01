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
	
	private String imgUrl;
	
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
	
	public BookResponseDto(String imgUrl, String isbn,  String title, Author author, Publisher publisher, double price, Language language, double discount) {
		this.imgUrl = imgUrl;
		this.isbn=isbn;
		this.title=title;
		this.author=(Author)author;
		this.publisher=(Publisher)publisher;
		this.price=price;
		this.language=(Language)language;
		this.discount=discount;
		
	}

	

	
}
