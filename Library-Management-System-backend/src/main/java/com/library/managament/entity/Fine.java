package com.library.managament.entity;

import jakarta.persistence.*;

@Entity
public class Fine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private boolean paid;

    @OneToOne
    @JoinColumn(name = "borrowing_id",unique=true)
    private Borrowing borrowing;

    public Fine() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Borrowing getBorrowing() {
		return borrowing;
	}

	public void setBorrowing(Borrowing borrowing) {
		this.borrowing = borrowing;
	}

    
}
