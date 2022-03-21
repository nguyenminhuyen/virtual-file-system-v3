package com.assignment.virtualfilesystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assignment.virtualfilesystem.rest.VFSErrorResponse;

@ControllerAdvice
public class VFSRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (VFSNotFoundException exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (VFSExistedNameException exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (VFSParentNotFoundException exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.NOT_ACCEPTABLE.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (VFSDatabaseException exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.SERVICE_UNAVAILABLE.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (VFSMismatchTypeException exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<VFSErrorResponse> handleException (Exception exc){
		
		VFSErrorResponse error = new VFSErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
