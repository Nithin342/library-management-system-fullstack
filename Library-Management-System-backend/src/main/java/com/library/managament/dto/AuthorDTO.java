package com.library.managament.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorDTO {

	private Long id;

	@NotBlank(message = "Author name cannot be empty")
    private String name;

    public AuthorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
