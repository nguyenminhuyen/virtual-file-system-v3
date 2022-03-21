package com.assignment.virtualfilesystem.exception;

public class VFSParentNotFoundException extends RuntimeException {
	
	public VFSParentNotFoundException() {
		
	}

	public VFSParentNotFoundException(String message) {
		super(message);
	}

	public VFSParentNotFoundException(Throwable cause) {
		super(cause);
	}

	public VFSParentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VFSParentNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
