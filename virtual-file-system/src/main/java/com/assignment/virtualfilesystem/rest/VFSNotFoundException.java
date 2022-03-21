package com.assignment.virtualfilesystem.rest;

public class VFSNotFoundException extends RuntimeException {

	public VFSNotFoundException() {
		
	}

	public VFSNotFoundException(String message) {
		super(message);
	}

	public VFSNotFoundException(Throwable cause) {
		super(cause);
	}

	public VFSNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
