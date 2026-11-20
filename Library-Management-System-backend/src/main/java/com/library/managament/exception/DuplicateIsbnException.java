package com.library.managament.exception;

public class DuplicateIsbnException extends RuntimeException{

   public DuplicateIsbnException(String message) {
	super(message);
}
}