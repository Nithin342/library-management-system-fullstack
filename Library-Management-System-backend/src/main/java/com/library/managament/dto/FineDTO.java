package com.library.managament.dto;

public class FineDTO {
	

    private Long id;
    private Long borrowingId;
    private double amount;
    private boolean paid;

    public FineDTO() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBorrowingId() {
		return borrowingId;
	}

	public void setBorrowingId(Long borrowingId) {
		this.borrowingId = borrowingId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
    
    

}
