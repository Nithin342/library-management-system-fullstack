package com.library.managament.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;


public class BorrowingDTO {

	private Long id;
	
	@NotNull(message = "Member ID is required")
	private Long memberId;
	
	@NotNull(message = "Book ID is required")
	private Long bookId;

	private String bookTitle;
	
	private LocalDate borrowDate;
	
	private LocalDate dueDate;
	
	private LocalDate returnDate;
	
	private String status;
	
	private long overdueDays;
	
	
	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	private double fine;
	

    public long getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(long overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BorrowingDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
}
