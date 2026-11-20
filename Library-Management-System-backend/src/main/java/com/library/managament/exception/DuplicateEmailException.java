package com.library.managament.exception;

public class DuplicateEmailException extends RuntimeException {

	public DuplicateEmailException(String  message) {
		super(message);
	}
}
