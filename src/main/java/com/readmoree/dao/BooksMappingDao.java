package com.readmoree.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.readmoree.entities.BooksMapping;
import com.readmoree.entities.BooksMappingId;
@Repository
public interface BooksMappingDao extends JpaRepository<BooksMapping, BooksMappingId	> {

}
