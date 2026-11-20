package com.library.managament.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books")
public class Book {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Title cannot be empty")	  
	    @Column(nullable = false)
	    private String title;
;

        @NotBlank(message = "Category cannot be empty")
        @Column(nullable = false)
        private String category;

	    @NotBlank(message = "ISBN cannot be empty")
	    @Column(nullable = false, unique = true)
	    private String isbn;

	    @NotNull(message = "Price is required")
	    @Positive(message = "Price must be greater than zero")
	    private Double price;

	    @NotNull(message = "Quantity is required")
	    @PositiveOrZero(message = "Quantity cannot be negative")
	    private Integer quantity;

	    @Column(nullable = false)
	    private Integer availableQuantity;
	    
	    @ManyToOne
	    @JoinColumn(name = "author_id")
	    private Author author;

	    public Book() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public Author getAuthor() {
			return author;
		}

		public void setAuthor(Author author) {
			this.author = author;
		}

		public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public String getIsbn() {
	        return isbn;
	    }

	    public void setIsbn(String isbn) {
	        this.isbn = isbn;
	    }

	    public Double getPrice() {
	        return price;
	    }

	    public void setPrice(Double price) {
	        this.price = price;
	    }

	    public Integer getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(Integer quantity) {
	        this.quantity = quantity;
	    }

	    public Integer getAvailableQuantity() {
	        return availableQuantity;
	    }

	    public void setAvailableQuantity(Integer availableQuantity) {
	        this.availableQuantity = availableQuantity;
	    }
}
