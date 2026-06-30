package com.spring.spring_rest.dto;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.Size;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class StudentDto {
	private Long id;
	
	@Size(min=2, max=50, message="First name should be between 2 and 50 characters")
	private String firstName;
	
	@Size(min=2, max=50, message="Last name should be between 2 and 50 characters")
	private String lastName;
	
	@Email(message="Email is required and should be valid")
	private String email;
	
	public StudentDto(Long id, String firstName, String lastName, 
			String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
