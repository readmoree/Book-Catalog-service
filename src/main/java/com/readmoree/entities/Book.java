package com.readmoree.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="books")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Book extends BaseEntity {
	
	@Column(length = 20, unique=true, nullable=false)
	private String isbn;
	
	@Column(length = 40)
	private String title;
	
	@Column(length = 20, nullable = false)
	private double price;
	
	@Column(name="publication_date")
	private LocalDate publicationDate;
	
	@Column
	private String description;
	
	@Column(name="page_count")
	private long pageCount;
	
	@Column(length = 20,columnDefinition = "VARCHAR(30)")
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Column(name= "img_url")
	private String image;
	
	@Column(length = 20,columnDefinition = "VARCHAR(20)")
	@Enumerated(EnumType.STRING)
	private Binding binding;
	
	//many books can belong to one author
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="publisher_id")
	private Publisher publisher;
	
	//for soft delete
	@Column(name="is_available",nullable = false, length=2)
	private boolean isAvailable;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BooksMapping> booksMappings;
	
	private double discount;
	
}
