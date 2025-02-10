package com.readmoree.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorRequestDto {
	
	@NotBlank(message = "first name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "last name cannot be blank")
	private String lastName;
	
	@NotBlank(message = "about section cannot be blank")
	private String about;
}
