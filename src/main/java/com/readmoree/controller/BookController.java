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
import com.readmoree.dtos.BookFilterRequestDto;
import com.readmoree.dtos.BookFilterResponseDTO;
import com.readmoree.dtos.BookRequestDto;
import com.readmoree.dtos.BookResponseDto;
import com.readmoree.entities.Labels;
import com.readmoree.service.BookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/book")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

		return ResponseEntity.ok(allBookList);

	}

	// get a particular book by id
	@GetMapping("/{bookId}")
	public ResponseEntity<?> getBookById(@PathVariable Long bookId){
		BookResponseDto book= bookService.getBookById(bookId);
		return ResponseEntity.ok(book);
	}

	//get book list based on array of book ids
	@GetMapping("/by-ids")
	public ResponseEntity<?> getBookListByIdArray(@RequestParam List<Long> bookIds){
		List<BookResponseDto> books = bookService.getBookListByIdArray(bookIds);
		return ResponseEntity.ok(books);
	}

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

	@PostMapping("/admin/add")
	public ResponseEntity<?> addBookToInventory(@RequestBody @Valid BookRequestDto bookDto){
		try {
			return ResponseEntity.ok(bookService.addBook(bookDto));
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
	@DeleteMapping("/admin/delete/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {

		return ResponseEntity.ok(bookService.deleteBookById(bookId));

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

	@PutMapping("/admin/update/{bookId}")
	public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody @Valid BookRequestDto bookDto){
		return ResponseEntity.ok(bookService.updateBook(bookId, bookDto));

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

		List<BookResponseDto> books = bookService.searchBooks(title, description, isbn, firstName, lastName);

		return ResponseEntity.ok(books);

	}

	// Fetch books by label, category, subcategory
	// Example Api call: /books/filter?category=Fiction&subCategory=Dystopian

	@GetMapping("/filter")

	public ResponseEntity<BookFilterResponseDTO> filterBooks(@ModelAttribute BookFilterRequestDto filterRequest) {
		BookFilterResponseDTO books = bookService.filterBooks(filterRequest);
		return ResponseEntity.ok(books);
	}
	
	public ResponseEntity<BookFilterResponseDTO> filterBooks(
	        @RequestParam(required = false) String label,
	        @RequestParam(required = false) String category,
	        @RequestParam(required = false) String subcategory) {
	    
	    // Decode and replace '%20' with spaces for all parameters
		if (label != null) {
	        label = label.replace("%20", "").replace(" ", "").trim();  // Remove spaces after replacing '%20' for label
	    }
		System.out.println(label);
	    
	    if (category != null) {
	        category = category.replace("%20", " ");  // Replace '%20' with space for category
	    }

	    if (subcategory != null) {
	        subcategory = subcategory.replace("%20", " ");  // Replace '%20' with space for subcategory
	    }
	    System.out.println("AFTER PRCOESSING");

	    // Convert the label to uppercase to match enum names
	    Labels labelEnum = null;
	    if (label != null) {
	        try {
	        	System.out.println(label.toUpperCase());
	            labelEnum = Labels.valueOf(label.toUpperCase()); // Convert to uppercase to match enum names
	            System.out.println(labelEnum);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(null); // Handle invalid enum values
	        }
	    }

	    BookFilterRequestDto filterRequest = new BookFilterRequestDto(labelEnum, category, subcategory);
	    BookFilterResponseDTO books = bookService.filterBooks(filterRequest);
	    return ResponseEntity.ok(books);
	}

}
