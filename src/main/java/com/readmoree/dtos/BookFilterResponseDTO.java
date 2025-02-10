package com.readmoree.dtos;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BookFilterResponseDTO {
    private List<BookResponseDto> books;
    private List<String> authors;
    private List<String> publishers;
    private List<String> languages;
    
    public BookFilterResponseDTO(List<BookResponseDto> books, List<String> authors, List<String> publishers, List<String> languages) {
    
    	this.books=books;
    	this.authors=authors;
    	this.publishers=publishers;
    	this.languages=languages;
    }
}
