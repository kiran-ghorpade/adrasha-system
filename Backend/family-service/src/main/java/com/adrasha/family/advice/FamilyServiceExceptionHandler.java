package com.adrasha.family.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.family.exception.FamilyAlreadyExistsException;
import com.adrasha.family.exception.FamilyNotFoundException;
import com.adrasha.family.exception.MemberAlreadyExistsException;
import com.adrasha.family.exception.MemberNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class FamilyServiceExceptionHandler {
	
	@ExceptionHandler(FamilyNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAshaNotFoundException(FamilyNotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FamilyAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingAshaException(FamilyAlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAshaNotFoundException(MemberNotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MemberAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleExistingAshaException(MemberAlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
