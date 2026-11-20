package com.library.managament.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class BookDTO {

    private Long id;

	@NotBlank(message = "Title cannot be empty")
    private String title;

	@NotNull(message = "authorid cannot be empty")
    private Long authorId;

	@NotBlank(message = "category cannot be empty")
	private String category;

	@NotBlank(message = "isbn cannot be empty")
    private String isbn;

	@NotNull(message = "Price is required")
	@Positive(message = "Price must be greater than zero")
    private Double price;

	@NotNull(message = "Quantity is required")
	@PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    private Integer availableQuantity;

    public BookDTO() {
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
    public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
}