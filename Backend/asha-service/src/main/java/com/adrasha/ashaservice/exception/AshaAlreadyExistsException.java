package com.adrasha.ashaservice.exception;

public class AshaAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AshaAlreadyExistsException(String message) {
		super(message);
	}
}
