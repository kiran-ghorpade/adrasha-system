package com.adrasha.user.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.user.exception.RoleRequestAlreadyExistsException;
import com.adrasha.user.exception.RoleRequestNotFoundException;
import com.adrasha.user.exception.UserAlreadyExistsException;
import com.adrasha.user.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UserServiceExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAshaNotFoundException(UserNotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingAshaException(UserAlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RoleRequestNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAshaNotFoundException(RoleRequestNotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RoleRequestAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingAshaException(RoleRequestAlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
