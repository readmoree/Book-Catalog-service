package com.readmoree.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookUpdateDto {

	@NotNull
	private double price;
	
	@NotNull
	private double discount;
	
	@NotNull
	private Long totalAvailableCount;

}
