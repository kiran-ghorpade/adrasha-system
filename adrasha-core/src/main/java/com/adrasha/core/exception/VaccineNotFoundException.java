package com.adrasha.core.exception;

public class VaccineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VaccineNotFoundException(String message) {
		super(message);
	}
}
