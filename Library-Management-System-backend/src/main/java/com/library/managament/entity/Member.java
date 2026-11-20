package com.library.managament.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="members")
public class Member {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Member name cannot be empty")
	    private String name;

	    @NotBlank(message = "Email cannot be empty")
	    @Email(message = "Invalid email format")
	    @Column(nullable = false, unique = true)
	    private String email;

	    public Member() {
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

		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
}
