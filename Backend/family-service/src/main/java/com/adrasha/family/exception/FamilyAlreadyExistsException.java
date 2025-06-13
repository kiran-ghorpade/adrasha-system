package com.adrasha.family.exception;

public class FamilyAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FamilyAlreadyExistsException(String message) {
		super(message);
	}
}
