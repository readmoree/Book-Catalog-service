package com.readmoree.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.readmoree.entities.Book;

public interface BookDao extends JpaRepository<Book, Long> {
	
//	List<Book> findByAuthorContainingIgnoreCase(String author);
//    List<Book> findByTitleContainingIgnoreCase(String title);
//    List<Book> findByDescriptionContainingIgnoreCase(String description);
//    List<Book> findByIsbn(String isbn);
    
	@Query("SELECT b FROM Book b JOIN b.author a WHERE " +
		       "(COALESCE(:title, '') = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
		       "(COALESCE(:description, '') = '' OR LOWER(b.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
		       "(COALESCE(:isbn, '') = '' OR b.isbn = :isbn) AND " +
		       "(COALESCE(:firstName, '') = '' OR LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
		       "(COALESCE(:lastName, '') = '' OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))")

     List<Book> searchBooks(@Param("title") String title, 
                            @Param("description") String description,
                            @Param("isbn") String isbn,
                            @Param("firstName") String firstName,
                            @Param("lastName") String lastName);

}
