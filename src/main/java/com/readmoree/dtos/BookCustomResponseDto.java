package com.readmoree.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookCustomResponseDto extends BaseEntity {
	
	private String image;
	
	private String isbn;
	
	private String title;

//	private double price;
	
//	private LocalDate publicationDate;
//	
//	private String description;
//
//	private long pageCount;
//
//	private Language language;
//	
//	private Binding binding;
//
//	private Author author;
//
//	private Publisher publisher;
//	
//	private double discount;

}
