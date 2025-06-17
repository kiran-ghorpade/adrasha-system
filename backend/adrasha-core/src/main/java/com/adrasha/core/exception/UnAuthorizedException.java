package com.adrasha.core.exception;

public class UnAuthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UnAuthorizedException() {
		super("Authorization needed for access.");
	}
}
