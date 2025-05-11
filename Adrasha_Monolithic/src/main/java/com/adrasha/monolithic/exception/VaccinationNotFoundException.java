package com.adrasha.monolithic.exception;

public class VaccinationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VaccinationNotFoundException(String message) {
		super(message);
	}
}
