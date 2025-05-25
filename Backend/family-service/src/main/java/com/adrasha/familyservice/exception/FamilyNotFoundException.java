package com.adrasha.familyservice.exception;

public class FamilyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FamilyNotFoundException(String message) {
		super(message);
	}
}
