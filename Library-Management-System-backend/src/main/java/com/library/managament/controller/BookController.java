package com.library.managament.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.BookDTO;
import com.library.managament.dto.BorrowingDTO;
import com.library.managament.entity.Book;
import com.library.managament.service.BookService;
import com.library.managament.service.BorrowingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	
	  private final BookService bookService;
	  private final BorrowingService borrowingService;

	  public BookController(
		        BookService bookService,
		        BorrowingService borrowingService) {

		    this.bookService = bookService;
		    this.borrowingService = borrowingService;
		}
	    @PostMapping
	    public BookDTO addBook(@Valid @RequestBody BookDTO book) {
	        return bookService.saveBook(book);
	    }

	    @GetMapping
	    public Page<BookDTO> getBooks(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "100") int size) {

	        return bookService.getBooksWithPagination(
	                page, size, "id", "asc");
	    }
	    
	    @GetMapping("/search")
	    public Page<BookDTO> searchBooks(
	            @RequestParam(required = false) String title,
	            @RequestParam(required = false) String category,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "100") int size) {

	        return bookService.searchBooksWithPagination(
	                title, category, page, size, "id", "asc");
	    }
	    
	    @GetMapping("/search/page")
	    public Page<BookDTO> searchBooksWithPagination(
	            @RequestParam(required = false) String title,
	            @RequestParam(required = false) String category,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(defaultValue = "id") String sortBy,
	            @RequestParam(defaultValue = "asc") String direction) {

	        return bookService.searchBooksWithPagination(
	                title,
	                category,
	                page,
	                size,
	                sortBy,
	                direction);
	    }
	    
	    @GetMapping("/page")
	    public Page<BookDTO> getBooksWithPagination(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(defaultValue = "id") String sortBy,
	            @RequestParam(defaultValue = "asc") String direction) {

	        return bookService.getBooksWithPagination(
	                page, size, sortBy, direction);
	    }
	    
	    
	    
	    @GetMapping("/{id}")
	    public BookDTO getBookById(@PathVariable Long id) {
	        return bookService.getBookById(id);
	    }
	    
	    @PutMapping("/{id}")
	    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO updatedBook) {
	        return bookService.updateBook(id, updatedBook);
	    }
	    
	    @GetMapping("/{id}/borrowings")
	    public List<BorrowingDTO> getBookBorrowings(
	            @PathVariable Long id) {

	        return borrowingService.getBorrowingsByBook(id);
	    }
	    
	 
	    
	    @DeleteMapping("/{id}")
	    public void deleteBook(@PathVariable Long id) {
	        bookService.deleteBook(id);
	    }

	 
}
