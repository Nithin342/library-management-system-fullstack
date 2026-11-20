package com.library.managament.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.managament.dto.BookDTO;
import com.library.managament.entity.Author;
import com.library.managament.entity.Book;
import com.library.managament.exception.BookCatalogLimitExceededException;
import com.library.managament.repository.AuthorRepository;
import com.library.managament.repository.BookRepository;

class BookServiceTest {

    private final BookRepository bookRepository = org.mockito.Mockito.mock(BookRepository.class);
    private final AuthorRepository authorRepository = org.mockito.Mockito.mock(AuthorRepository.class);
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, authorRepository);
    }

    @Test
    void rejectsNewBookWhenCatalogIsAtLimit() {
        when(bookRepository.existsByIsbn("isbn")).thenReturn(false);
        when(bookRepository.count()).thenReturn(5_000L);

        assertThrows(
                BookCatalogLimitExceededException.class,
                () -> bookService.saveBook(validBook()));

        verify(bookRepository, never()).save(any(Book.class));
        verifyNoInteractions(authorRepository);
    }

    @Test
    void allowsNewBookWhenCatalogHasRoom() {
        Author author = new Author();
        author.setId(1L);
        when(bookRepository.existsByIsbn("isbn")).thenReturn(false);
        when(bookRepository.count()).thenReturn(4_999L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookDTO result = bookService.saveBook(validBook());

        assertEquals("isbn", result.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    private BookDTO validBook() {
        BookDTO book = new BookDTO();
        book.setTitle("Test book");
        book.setAuthorId(1L);
        book.setCategory("Test");
        book.setIsbn("isbn");
        book.setPrice(10.0);
        book.setQuantity(1);
        return book;
    }
}
