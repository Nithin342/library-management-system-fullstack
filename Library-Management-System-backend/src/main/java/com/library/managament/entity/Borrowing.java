package com.library.managament.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "borrowings")
public class Borrowing {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "member_id", nullable = false)
	    private Member member;

	    @ManyToOne
	    @JoinColumn(name = "book_id", nullable = false)
	    private Book book;

	    @Column(nullable = false)
	    private LocalDate borrowDate;
	    
	    @Column(nullable = false)
	    private LocalDate dueDate;

	    
		private LocalDate returnDate;

		@Column(nullable = false)
	    @Enumerated(EnumType.STRING)
	    private BorrowingStatus status;

	    public Borrowing() {
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Member getMember() {
			return member;
		}

		public void setMember(Member member) {
			this.member = member;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
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

		public BorrowingStatus getStatus() {
			return status;
		}

		public void setStatus(BorrowingStatus status) {
			this.status = status;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}

	    
	    
}
