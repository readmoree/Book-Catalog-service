package com.readmoree.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PublisherRequestDto {

	@NotBlank(message = "Publisher name must not be null")
	private String name;
	
	@NotBlank(message = "Publisher email must not be null")
	private String email;
	
	@NotBlank(message = "Publisher mobile number must not be null")
	private String mobileNo;
}
