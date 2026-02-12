package com.learn.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long Id;

	@NotBlank(message = "Enter the Username")
	String userName;

	@NotBlank(message = "Password cannot be empty")
	String Password;

	@NotBlank(message = "Email is Required")
	@Pattern(regexp = "^[A-Za-z0-9_%+-]+@gmail\\.(com|in)$",
	message ="Enter Valid Email")
	
	String Email;


	String Role;

	@PrePersist
	public void setDefaultRole() {
		if (this.Role == null || this.Role.isBlank()) {
			this.Role = "USER";
		}
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public Users() {
	}

	public Users(String UserName, String password, String email, String role) {
		super();
		userName = UserName;
		Password = password;
		Email = email;
		Role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String UserName) {
		userName = UserName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
