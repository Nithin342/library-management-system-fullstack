package com.library.managament.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.managament.dto.AuthorDTO;
import com.library.managament.dto.BookDTO;
import com.library.managament.service.AuthorService;
import com.library.managament.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	private final AuthorService authorService;
	private final BookService bookService;

	public AuthorController(
	        AuthorService authorService, BookService bookService) {

	    this.authorService = authorService;
	    this.bookService = bookService;
	}

    @PostMapping
    public AuthorDTO addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.saveAuthor(authorDTO);
    }
    
    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    
    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }
    
    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO updatedAuthor) {

        return authorService.updateAuthor(id, updatedAuthor);
    }
    
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
    @GetMapping("/{id}/books")
    public List<BookDTO> getBooksByAuthor(@PathVariable Long id) {
        return bookService.getBooksByAuthor(id);
    }
    
}
