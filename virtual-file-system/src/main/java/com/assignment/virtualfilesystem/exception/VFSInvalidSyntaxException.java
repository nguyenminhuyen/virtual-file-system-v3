package com.assignment.virtualfilesystem.exception;

public class VFSInvalidSyntaxException extends RuntimeException {

	public VFSInvalidSyntaxException() {
		
	}

	public VFSInvalidSyntaxException(String message) {
		super(message);
	}

	public VFSInvalidSyntaxException(Throwable cause) {
		super(cause);
	}

	public VFSInvalidSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSInvalidSyntaxException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}