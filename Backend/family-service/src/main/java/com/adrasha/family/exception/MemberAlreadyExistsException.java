package com.adrasha.family.exception;

public class MemberAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MemberAlreadyExistsException(String message) {
		super(message);
	}
}
