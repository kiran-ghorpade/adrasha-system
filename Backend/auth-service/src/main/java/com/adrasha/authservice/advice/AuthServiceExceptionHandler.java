package com.adrasha.authservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.authservice.dto.ErrorResponse;
import com.adrasha.authservice.exception.UserAlreadyExistsException;
import com.adrasha.authservice.exception.UserNotFoundException;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
public class AuthServiceExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "User already exists")
		})
	public ResponseEntity<ErrorResponse> handleExistingUserException(UserAlreadyExistsException  ex) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .errorCode(HttpStatus.CONFLICT.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User Not Found")
		})
	public ResponseEntity<ErrorResponse> handleExistingUserException(UserNotFoundException  ex) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errorCode(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(JwtException.class)
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "JWT Token Invalid")
		})
	public ResponseEntity<ErrorResponse> handleExistingUserException(JwtException  ex) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorCode(HttpStatus.UNAUTHORIZED.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	// Handler For validation exceptions
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthExceptions(AuthenticationException ex) {
	    
	     ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorCode(HttpStatus.UNAUTHORIZED.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}
