package com.readmoree.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksMappingId implements Serializable {

    @Column(name = "book_id")
    private Long bookId;

    @Enumerated(EnumType.STRING)
    private Labels labels;

    private String category;
    private String subCategory;
}