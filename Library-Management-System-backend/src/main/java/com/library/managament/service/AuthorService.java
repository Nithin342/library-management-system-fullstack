package com.library.managament.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.managament.dto.AuthorDTO;
import com.library.managament.entity.Author;
import com.library.managament.exception.AuthorDeletionException;
import com.library.managament.exception.AuthorNotFoundException;
import com.library.managament.repository.AuthorRepository;
import com.library.managament.repository.BookRepository;

@Service
public class AuthorService {
	
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;

	public AuthorService(
	        AuthorRepository authorRepository,
	        BookRepository bookRepository) {

	    this.authorRepository = authorRepository;
	    this.bookRepository = bookRepository;
	}
    
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {

        Author author = new Author();

        author.setName(authorDTO.getName());

        Author savedAuthor = authorRepository.save(author);

        AuthorDTO responseDTO = new AuthorDTO();

        responseDTO.setId(savedAuthor.getId());
        responseDTO.setName(savedAuthor.getName());

        return responseDTO;
    }
    
    public List<AuthorDTO> getAllAuthors() {

        return authorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    public AuthorDTO getAuthorById(Long id) {

        Author author = authorRepository.findById(id)
        		.orElseThrow(() ->
                new AuthorNotFoundException(
                        "Author not found with id: " + id));

        return convertToDTO(author);
    }
    
    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthor) {

        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + id));

        existingAuthor.setName(updatedAuthor.getName());

        Author savedAuthor = authorRepository.save(existingAuthor);

        return convertToDTO(savedAuthor);
    }
    
    public void deleteAuthor(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException(
                                "Author not found with id: " + id));

        boolean hasBooks =
                bookRepository.findByAuthorId(id)
                        .stream()
                        .findAny()
                        .isPresent();

        if (hasBooks) {
            throw new AuthorDeletionException(
                    "Cannot delete author because the author has books");
        }

        authorRepository.delete(author);
    }
    
    private AuthorDTO convertToDTO(Author author) {

        AuthorDTO dto = new AuthorDTO();

        dto.setId(author.getId());
        dto.setName(author.getName());

        return dto;
    }
    

   
}
