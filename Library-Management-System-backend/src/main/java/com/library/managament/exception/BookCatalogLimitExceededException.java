package com.library.managament.exception;

public class BookCatalogLimitExceededException extends RuntimeException {

    public BookCatalogLimitExceededException(String message) {
        super(message);
    }
}
