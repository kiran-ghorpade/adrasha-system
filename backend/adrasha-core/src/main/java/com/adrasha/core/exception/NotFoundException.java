package com.adrasha.core.exception;


public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotFoundException() {
		super("Entity Not Found");
	}
	
	public NotFoundException(String message) {
		super(message);
	}

}
