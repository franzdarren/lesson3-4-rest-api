package com.spring.spring_rest.entity;

public class StudentErrorResponse {
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	private int status;
	private String message;
	private long timeStamp;
	
	public StudentErrorResponse() {
		
	}
	public StudentErrorResponse(int status, String message, long timestamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timestamp;
	}
}
