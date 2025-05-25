package com.adrasha.pregnancyservice.exception;

public class PregnancyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PregnancyNotFoundException(String message) {
		super(message);
	}
}
