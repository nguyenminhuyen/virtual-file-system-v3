package com.assignment.virtualfilesystem.exception;

public class VFSInvalidNameException extends RuntimeException {

	public VFSInvalidNameException() {
		
	}

	public VFSInvalidNameException(String message) {
		super(message);
	}

	public VFSInvalidNameException(Throwable cause) {
		super(cause);
	}

	public VFSInvalidNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSInvalidNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
