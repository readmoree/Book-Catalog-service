package com.readmoree.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readmoree.dtos.ApiResponse;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
	
	private BookService bookService;
	
	/**
	 * get all books
	 * user should be able to view all books
	 *
	 * Desc - display all books
	 * URL - http://host:port/book/all
	 * Method - GET
	 * Payload - empty
	 * success Resp -SC 200, ok JSON representation of List of Books
	 * failure(empty list) resp - SC 204
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllBooks(){
		List<BookResponseDto> allBookList=bookService.getAllBooks();
		
		if (allBookList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allBookList);
        }
	}
	
	
	
	// get a particular book by id
	
	// add a book
	/**
	 * Admin should be able to add book
	 * i/p: Bookrequest dto, userId(adminId)
	 * 
	 * desc: add book
	 * URL- http://host:port/book/add
	 * Method - POST
	 * payload - bookdto
	 * success - sc 201, created.
	 * failure- SC 500
	 */
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<?> addBookToInventory(@PathVariable Long userId, @RequestBody @Valid BookRequestDto bookDto){
		try {
			return ResponseEntity.ok(bookService.addBook(userId, bookDto));
		}catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
		}
	}
	
	//delete a book
	/**
	 * admin should delete a book 
	 * i/p: userId, bookId
	 * 
	 * Desc - delete book
	 * URL - http://host:port/book/delete/{userId}/{bookId}
	 * Method - DELETE
	 * payload - empty
	 * success - SC 200, with updated bookResponseDto
	 * failure - SC 404
	 */
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
      
            return ResponseEntity.ok(bookService.deleteBookById(id));
   
    }
	
	// update details of a book
	/**
	 * admin should update book details
	 * i/p: boolreqdto
	 * 
	 * Desc - update book details
	 * URL - http://host:port/book/update-price/{userId}/{bookId}
	 * Method - PUT
	 * payload - price
	 * success - SC 200, with response msg
	 * failure - SC 500
	 */
	
	@PutMapping("/update/{userId}/{bookId}")
	public ResponseEntity<?> updateBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody @Valid BookRequestDto bookDto){
			return ResponseEntity.ok(bookService.updateBook(userId, bookId, bookDto));
		
	}
	
	
	// find List of books by - author name, title, description, ISBN 
	/**
	 * user should be able to find List of books by author name, title, description, ISBN
	 * i/p: title, description, isbn, fisrtName, lastName
	 * 
	 * Desc - get the list of books/ book(ISBN)
	 * URL - http://host:port/book/search
	 * Method - GET
	 * payload - title, description, isbn, fisrtName, lastName(any or all)
	 * success - SC 200, with List of book/s
	 * failure - SC 404
	 */
	
	
	@GetMapping("/search")
    public ResponseEntity<?> searchBooks(
    		@RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
		
		
		System.out.println("Searching books with: " +
	            "title=" + title + ", description=" + description +
	            ", isbn=" + isbn + ", firstName=" + firstName + ", lastName=" + lastName);

        List<BookResponseDto> books = bookService.searchBooks(title, description, isbn, firstName, lastName);

        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(books);
        }
    }
	
	
	
	
	
	
	
}
