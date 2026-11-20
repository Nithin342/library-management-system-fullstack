package com.library.managament.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.library.managament.dto.BookDTO;
import com.library.managament.entity.Author;
import com.library.managament.entity.Book;
import com.library.managament.exception.AuthorNotFoundException;
import com.library.managament.exception.BookCatalogLimitExceededException;
import com.library.managament.exception.BookDeletionException;
import com.library.managament.exception.BookNotFoundException;
import com.library.managament.exception.DuplicateIsbnException;
import com.library.managament.exception.InvalidBookQuantityException;
import com.library.managament.exception.InvalidPaginationException;
import com.library.managament.exception.InvalidSortFieldException;
import com.library.managament.repository.AuthorRepository;
import com.library.managament.repository.BookRepository;

@Service
public class BookService {

    private static final long MAX_BOOK_RECORDS = 5_000;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // CREATE
    public BookDTO saveBook(BookDTO bookDTO) {
    	
    	if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
    	    throw new DuplicateIsbnException(
    	            "Book already exists with ISBN: "
    	            + bookDTO.getIsbn());
    	}

        if (bookRepository.count() >= MAX_BOOK_RECORDS) {
            throw new BookCatalogLimitExceededException(
                    "The catalog is full. It can contain at most 5,000 books.");
        }

    	  Author author = authorRepository.findById(bookDTO.getAuthorId())
    	            .orElseThrow(() ->
    	                    new AuthorNotFoundException(
    	                            "Author not found with id: "
    	                            + bookDTO.getAuthorId()));
        // DTO -> Entity
        Book book = new Book();

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setCategory(bookDTO.getCategory());
        book.setIsbn(bookDTO.getIsbn());
        book.setPrice(bookDTO.getPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setAvailableQuantity(bookDTO.getQuantity());

        // Save Entity
        Book savedBook = bookRepository.save(book);

        // Entity -> DTO
        BookDTO responseDTO = new BookDTO();

        responseDTO.setId(savedBook.getId());
        responseDTO.setTitle(savedBook.getTitle());
        responseDTO.setAuthorId(savedBook.getAuthor().getId());
        responseDTO.setCategory(savedBook.getCategory());
        responseDTO.setIsbn(savedBook.getIsbn());
        responseDTO.setPrice(savedBook.getPrice());
        responseDTO.setQuantity(savedBook.getQuantity());
        responseDTO.setAvailableQuantity(savedBook.getAvailableQuantity());

        return responseDTO;
    }

    // GET BY ID
    public BookDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        return convertToDTO(book);
    }
    
    // UPDATE
    public BookDTO updateBook(Long id, BookDTO updatedBook) {
    	
    	if (bookRepository.existsByIsbnAndIdNot(
    	        updatedBook.getIsbn(), id)) {

    	    throw new DuplicateIsbnException(
    	            "Book already exists with ISBN: "
    	            + updatedBook.getIsbn());
    	}
    	
    	 Book existingBook = bookRepository.findById(id)
    	            .orElseThrow(() ->
    	                    new BookNotFoundException(
    	                            "Book not found with id: " + id));

    	Author author = authorRepository.findById(updatedBook.getAuthorId())
    	        .orElseThrow(() ->
    	                new AuthorNotFoundException(
    	                        "Author not found with id: " + updatedBook.getAuthorId()));

    	existingBook.setTitle(updatedBook.getTitle());

    	existingBook.setAuthor(author);

    	existingBook.setCategory(updatedBook.getCategory());

    	existingBook.setIsbn(updatedBook.getIsbn());

    	existingBook.setPrice(updatedBook.getPrice());

    	int borrowedQuantity =
    	        existingBook.getQuantity()
    	        - existingBook.getAvailableQuantity();

    	if (updatedBook.getQuantity() < borrowedQuantity) {
    	    throw new InvalidBookQuantityException(
    	            "Quantity cannot be less than currently borrowed quantity: "
    	            + borrowedQuantity);
    	}

    	existingBook.setQuantity(updatedBook.getQuantity());

    	existingBook.setAvailableQuantity(
    	        updatedBook.getQuantity() - borrowedQuantity);

    	Book savedBook = bookRepository.save(existingBook);

    	return convertToDTO(savedBook);
    }

    // DELETE
    public void deleteBook(Long id) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " + id));

        int borrowedQuantity =
                existingBook.getQuantity()
                - existingBook.getAvailableQuantity();

        if (borrowedQuantity > 0) {
            throw new BookDeletionException(
                    "Cannot delete book because "
                    + borrowedQuantity
                    + " copy/copies are currently borrowed");
        }

        bookRepository.delete(existingBook);
    }
    
    public List<BookDTO> getBooksByAuthor(Long authorId) {

        authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + authorId));

        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    public Page<BookDTO> getBooksWithPagination(
            int page,
            int size,
            String sortBy,
            String direction) {

    	validatePagination(page, size);
        validateSortField(sortBy);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
    
    public Page<BookDTO> searchBooksWithPagination(
            String title,
            String category,
            int page,
            int size,
            String sortBy,
            String direction) {

    	validatePagination(page, size);
    	validateSortField(sortBy);
    	
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.searchBooks(
                title,
                category,
                pageable
        ).map(this::convertToDTO);
    }
    
    private void validateSortField(String sortBy) {

        List<String> allowedFields = List.of(
                "id",
                "title",
                "category",
                "isbn",
                "price",
                "quantity",
                "availableQuantity"
        );

        if (!allowedFields.contains(sortBy)) {
            throw new InvalidSortFieldException(
                    "Invalid sort field: " + sortBy);
        }
    }
    
    private void validatePagination(int page, int size) {

        if (page < 0) {
            throw new InvalidPaginationException(
                    "Page number cannot be negative");
        }

        if (size <= 0) {
            throw new InvalidPaginationException(
                    "Page size must be greater than 0");
        }

        if (size > 100) {
            throw new InvalidPaginationException(
                    "Page size cannot exceed 100");
        }
    }
    
    

    // ENTITY -> DTO
    private BookDTO convertToDTO(Book book) {

        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthorId(book.getAuthor() == null ? null : book.getAuthor().getId());
        dto.setCategory(book.getCategory());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setQuantity(book.getQuantity());
        dto.setAvailableQuantity(book.getAvailableQuantity());

        return dto;
    }
}