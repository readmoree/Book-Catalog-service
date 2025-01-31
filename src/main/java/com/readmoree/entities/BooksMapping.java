package com.readmoree.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//For mapping single book with multiple combinations of labels, categories and sub categories 
public class BooksMapping {

    @EmbeddedId
    private BooksMappingId id;

    @ManyToOne
    @MapsId("bookId")  // This ensures that bookId is mapped correctly without duplication
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    private Book book;
}

