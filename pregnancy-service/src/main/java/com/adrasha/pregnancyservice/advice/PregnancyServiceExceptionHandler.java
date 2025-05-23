package com.adrasha.pregnancyservice.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.pregnancyservice.dto.ApiError;
import com.adrasha.pregnancyservice.dto.ErrorResponse;
import com.adrasha.pregnancyservice.dto.ValidationErrorResponse;
import com.adrasha.pregnancyservice.exception.PregnancyNotFoundException;

@ControllerAdvice
public class PregnancyServiceExceptionHandler {
	
	// Handler For validation exceptions
	@ExceptionHandler(PregnancyNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleExistingUserException(PregnancyNotFoundException  ex) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ex.getClass().getName())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// Handler For validation exceptions
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
	    // Collecting all validation errors
	    List<ApiError> errors = ex.getBindingResult()
	    		.getFieldErrors()
	    		.stream()
	    		.map(fieldError -> 
	    				ApiError.builder()
	    				.field(fieldError.getField()) // This gives the field name
	                    .message(fieldError.getDefaultMessage()) // This gives the validation error message
	                    .build())
	    		.collect(Collectors.toList());
	    
	    ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(HttpStatus.BAD_REQUEST.name())
                .message("Validation failed")
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//		System.out.println(ex.getStackTrace());
//		
//        // Create error response
//        ErrorResponse response = ErrorResponse.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
//                .message(ex.getStackTrace().toString())
//                .build();
//        
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
