package com.assignment.virtualfilesystem.exception;

public class VFSDatabaseException extends RuntimeException {
	
	public VFSDatabaseException() {
		
	}

	public VFSDatabaseException(String message) {
		super(message);
	}

	public VFSDatabaseException(Throwable cause) {
		super(cause);
	}

	public VFSDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSDatabaseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}