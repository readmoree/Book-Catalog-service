package com.readmoree.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.readmoree.dtos.BookResponseDto;
import com.readmoree.entities.Book;
import com.readmoree.entities.Labels;

public interface BookDao extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b JOIN b.author a WHERE " +
			"(COALESCE(:searchKeyword, '') = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))) OR " +
			"(COALESCE(:searchKeyword, '') = '' OR LOWER(b.description) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))) OR " +
			"(COALESCE(:searchKeyword, '') = '' OR b.isbn = :searchKeyword) OR " +
			"(COALESCE(:searchKeyword, '') = '' OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :searchKeyword, '%'))) OR " +
			"(COALESCE(:searchKeyword, '') = '' OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :searchKeyword, '%')))")

	List<Book> searchBooks(@Param("searchKeyword") String searchKeyword);

	@Query("SELECT DISTINCT b FROM Book b " +
			"JOIN b.booksMappings bm " +
			"WHERE (:labels IS NULL OR bm.id.labels = :labels) " +
			"AND (:category IS NULL OR bm.id.category = :category) " +
			"AND (:subCategory IS NULL OR bm.id.subCategory = :subCategory) ")
	List<Book> filterBooks(
			@Param("labels") Labels labels, 
			@Param("category") String category,
			@Param("subCategory") String subCategory
			);

	@Query("""
			    SELECT new com.readmoree.dtos.BookResponseDto(
			        b.id, b.image, b.isbn, b.title, b.author, b.publisher, 
			        b.price, b.language, b.discount,b.totalAvailableCount,
			        b.createdOn, b.updatedOn,b.publicationDate,b.description, b.binding, 
			        bm.id.labels, bm.id.category, bm.id.subCategory)
			    FROM Book b
			    JOIN b.booksMappings bm
			    WHERE b.isAvailable = true
			""")
	List<BookResponseDto> findAllBooksWithMappings();



}
