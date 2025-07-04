package com.adrasha.core.exception;


public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyExistsException() {
		super("Entity Already Exist");
	}
	
	public AlreadyExistsException(String message) {
		super(message);
	}

}
