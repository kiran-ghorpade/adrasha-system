package com.adrasha.user.exception;

public class RoleRequestAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoleRequestAlreadyExistsException(String message) {
		super(message);
	}
}
