package com.adrasha.auth.exception;

import io.jsonwebtoken.JwtException;

public class JwtValidationException extends JwtException{

	private static final long serialVersionUID = 1L;

	public JwtValidationException(String message) {
		super("JwtValidationException : "+message);
	}
}
