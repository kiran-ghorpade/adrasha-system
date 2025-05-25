package com.adrasha.core.exception;


public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super("User Not Found");
	}
	
	public UserNotFoundException(Object message) {
		super("User Not Found with "+message);
	}

}
