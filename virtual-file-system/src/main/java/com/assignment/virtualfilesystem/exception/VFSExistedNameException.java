package com.assignment.virtualfilesystem.exception;

public class VFSExistedNameException extends RuntimeException {

	public VFSExistedNameException() {
		
	}

	public VFSExistedNameException(String message) {
		super(message);
	}

	public VFSExistedNameException(Throwable cause) {
		super(cause);
	}

	public VFSExistedNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSExistedNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}