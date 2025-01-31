package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readmoree.entities.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {
	
	Author findByLastNameOrFirstName(String lastName, String firstName);
}
