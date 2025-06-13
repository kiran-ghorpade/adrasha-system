package com.adrasha.auth.exception;


public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException() {
		super("User Not Found");
	}
	
	public NotFoundException(Object message) {
		super("User Not Found with "+message);
	}

}
