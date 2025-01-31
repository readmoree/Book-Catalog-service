package com.readmoree.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity {

	@Column(name="first_name",nullable = false, length=40)
	private String firstName;
	
	@Column(name="last_name",nullable = false, length=40)
	private String lastName;
	
	private String about;
}
