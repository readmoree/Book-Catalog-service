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
public class BookResponseDto extends BaseEntity {
	
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
}
