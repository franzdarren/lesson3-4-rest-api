package com.spring.spring_rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.spring_rest.entity.StudentErrorResponse;
import com.spring.spring_rest.exception.StudentNotFoundException;

@ControllerAdvice
public class StudentExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(
			StudentNotFoundException ex){
		StudentErrorResponse err = new StudentErrorResponse();
		
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setMessage(ex.getMessage());
		err.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {
		StudentErrorResponse err = new StudentErrorResponse();

		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setMessage(ex.getMessage());
		err.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(
			MethodArgumentNotValidException ex){
		Map<String, String>err = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			err.put(error.getField(), error.getDefaultMessage());
		});

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
}
