package com.assignment.virtualfilesystem.rest;

public class VFSResponseMessage {
	
	private String message;
	
	public VFSResponseMessage() {
		
	}

	public VFSResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
