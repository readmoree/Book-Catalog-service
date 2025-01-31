package com.readmoree.dtos;

import com.readmoree.entities.Labels;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookFilterRequestDto {
    private Labels labels;
    private String category;
    private String subCategory;
   
}
	