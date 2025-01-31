package com.readmoree.dtos;

import java.time.LocalDate;

import com.readmoree.entities.Binding;
import com.readmoree.entities.Language;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
	
	@Size(min = 13, max = 13, message = "Invalid isbn length!")
	private String isbn;
	
	@NotBlank(message="title must not be blank")
	private String title;

	@NotNull(message="price must not be null")
	private double price;
	
	private LocalDate publicationDate;
	
	private String description;
	
	@NotNull(message="page count must not be null")
	private long pageCount;

	private Language language;

	@Enumerated(EnumType.STRING)
	private Binding binding;
	
	@NotNull(message="Author id must not be null")
	private Long authorId;
	
	@NotNull(message="Available status must not be null")
	private boolean isAvailable;
	
	@NotNull(message="Publisher id must not be null")
	private Long publisherId;
	

}
