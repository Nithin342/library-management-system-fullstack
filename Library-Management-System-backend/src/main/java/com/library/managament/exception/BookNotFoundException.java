package com.library.managament.exception;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(String message) {
        super(message);
    }
}
