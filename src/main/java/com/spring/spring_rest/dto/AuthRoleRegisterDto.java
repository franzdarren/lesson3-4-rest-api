package com.spring.spring_rest.dto;

public class AuthRoleRegisterDto {
	private String name;
	
	public AuthRoleRegisterDto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
