package com.adrasha.monolithic.exception;

public class VaccineNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VaccineNotFoundException(String message) {
		super(message);
	}
}
