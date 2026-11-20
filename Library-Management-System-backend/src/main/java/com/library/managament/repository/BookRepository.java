package com.library.managament.repository;


import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.library.managament.entity.*;

public interface BookRepository extends JpaRepository<Book, Long> {
	
	List<Book> findByAuthorId(Long authorId);
	
	boolean existsByIsbn(String isbn);
	
	boolean existsByIsbnAndIdNot(String isbn, Long id);
	
	@Query("""
		    SELECT b FROM Book b
		    WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
		    AND (:category IS NULL OR LOWER(b.category) = LOWER(:category))
		""")
		Page<Book> searchBooks(
		        @Param("title") String title,
		        @Param("category") String category,
		        Pageable pageable);
 }



