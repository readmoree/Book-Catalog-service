package com.readmoree.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorResponseDto extends BaseEntity {
	
	private String firstName;
	
	private String lastName;
	
	private String about;
}
