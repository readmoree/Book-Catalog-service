package com.readmoree.dtos;

import java.util.List;

import com.readmoree.entities.Language;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookFilterResponseDTO {
    private List<BookResponseDto> books;
    private List<String> authors;
    private List<String> publishers;
    private List<String> languages;
}
