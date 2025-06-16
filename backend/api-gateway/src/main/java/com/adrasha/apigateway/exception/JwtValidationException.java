package com.adrasha.apigateway.exception;

public class JwtValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JwtValidationException(String message) {
		super("JwtValidationException : "+message);
	}
}
