package com.adrasha.auth.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.auth.dto.ErrorResponse;
import com.adrasha.auth.exception.UserAlreadyExistsException;
import com.adrasha.auth.exception.UserNotFoundException;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AuthServiceExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingUserException(UserAlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleExistingUserException(UserNotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
				.error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorResponse> handleExistingUserException(JwtException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	// Handler For validation exceptions
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthExceptions(AuthenticationException ex, HttpServletRequest request) {
	    
	     ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	

//	// Handler For validation exceptions
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorResponse> handleAllExceptions(AuthenticationException ex, HttpServletRequest request) {
//	    
//		ex.printStackTrace();
//		
//	     ErrorResponse response = ErrorResponse.builder()
//                .status(HttpStatus.UNAUTHORIZED.value())
//                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
//                .path(request.getRequestURI())
//                .message("Unexpected Internal Server Error")
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//	}
}
