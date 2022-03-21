package com.assignment.virtualfilesystem.exception;

public class VFSMismatchTypeException extends RuntimeException {

	public VFSMismatchTypeException() {
		
	}

	public VFSMismatchTypeException(String message) {
		super(message);
	}

	public VFSMismatchTypeException(Throwable cause) {
		super(cause);
	}

	public VFSMismatchTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSMismatchTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
