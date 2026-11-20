package com.library.managament.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

      @ExceptionHandler(BookCatalogLimitExceededException.class)
      public ResponseEntity<String> handleBookCatalogLimitExceeded(
              BookCatalogLimitExceededException ex) {
          return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
      }

	  @ExceptionHandler(BookNotFoundException.class)
	    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
	  
	  @ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<Map<String, String>> handleValidationExceptions(
	          MethodArgumentNotValidException ex) {

	      Map<String, String> errors = new HashMap<>();

	      ex.getBindingResult().getFieldErrors().forEach(error -> {
	          errors.put(error.getField(), error.getDefaultMessage());
	      });

	      return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(AuthorNotFoundException.class)
	  public ResponseEntity<String> handleAuthorNotFoundException(
	          AuthorNotFoundException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(MemberNotFoundException.class)
	  public ResponseEntity<String> handleMemberNotFoundException(
	          MemberNotFoundException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.NOT_FOUND);
	  }
	  @ExceptionHandler(BorrowingNotFoundException.class)
	  public ResponseEntity<String> handleBorrowingNotFoundException(
	          BorrowingNotFoundException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(BookNotAvailableException.class)
	  public ResponseEntity<String> handleBookNotAvailableException(
	          BookNotAvailableException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(AlreadyReturnedException.class)
	  public ResponseEntity<String> handleAlreadyReturnedException(
	          AlreadyReturnedException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(AlreadyBorrowedException.class)
	  public ResponseEntity<String> handleAlreadyBorrowedException(
	          AlreadyBorrowedException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(InvalidSortFieldException.class)
	  public ResponseEntity<String> handleInvalidSortFieldException(
	          InvalidSortFieldException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(InvalidBorrowingStatusException.class)
	  public ResponseEntity<String> handleInvalidBorrowingStatusException(
	          InvalidBorrowingStatusException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(FineNotFoundException.class)
	  public ResponseEntity<String> handleFineNotFoundException(
	          FineNotFoundException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(FineAlreadyPaidException.class)
	  public ResponseEntity<String> handleFineAlreadyPaidException(
	          FineAlreadyPaidException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  @ExceptionHandler(InvalidPaginationException.class)
	  public ResponseEntity<String> handleInvalidPaginationException(
	          InvalidPaginationException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(InvalidBookQuantityException.class)
	  public ResponseEntity<String> handleInvalidBookQuantityException(
	          InvalidBookQuantityException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(BookDeletionException.class)
	  public ResponseEntity<String> handleBookDeletionException(
	          BookDeletionException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(MemberDeletionException.class)
	  public ResponseEntity<String> handleMemberDeletionException(
	          MemberDeletionException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  @ExceptionHandler(AuthorDeletionException.class)
	  public ResponseEntity<String> handleAuthorDeletionException(
	          AuthorDeletionException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(DuplicateIsbnException.class) 
	  public ResponseEntity<String> handleDuplicateIsbnException(
			  DuplicateIsbnException ex) { 
		  return new ResponseEntity<>(
				  ex.getMessage(),
				  HttpStatus.BAD_REQUEST); 
	 }
	  
	  @ExceptionHandler(DuplicateEmailException.class)
	  public ResponseEntity<String> handleDuplicateEmailException(
	          DuplicateEmailException ex) {

	      return new ResponseEntity<>(
	              ex.getMessage(),
	              HttpStatus.BAD_REQUEST);
	  }

@ExceptionHandler(InvalidCredentialsException.class)
public ResponseEntity<String> handleInvalidCredentials(
        InvalidCredentialsException ex) {

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ex.getMessage());
}
}