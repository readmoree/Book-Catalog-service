package com.readmoree.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.readmoree.entities.BooksMapping;
import com.readmoree.entities.BooksMappingId;
import com.readmoree.entities.Labels;

@Repository
public interface BooksMappingDao extends JpaRepository<BooksMapping, BooksMappingId	> {
	
	 @Query("SELECT DISTINCT b.id.labels FROM BooksMapping b")
	    List<Labels> findDistinctLabels();
	 
	 @Query("SELECT DISTINCT b.id.category from BooksMapping b where b.id.labels=:lable")
	 List<String> findDistinctCategoryByLabel(@Param("lable") Labels lable);

	 @Query("SELECT DISTINCT b.id.subCategory from BooksMapping b where b.id.labels=:label AND b.id.category=:category")
	 List<String> findDistinctSubCategoryByLabelAndCategory(@Param("label") Labels label, @Param("category") String category);
	 
	 
}
