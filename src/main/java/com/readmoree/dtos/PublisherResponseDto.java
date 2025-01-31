package com.readmoree.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PublisherResponseDto extends BaseEntity {

	private String name;
	
	private String email;
	
	private String mobileNo;
}
