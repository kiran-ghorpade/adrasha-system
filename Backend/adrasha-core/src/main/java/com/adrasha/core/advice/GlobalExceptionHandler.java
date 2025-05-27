package com.adrasha.core.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.core.dto.ApiError;
import com.adrasha.core.dto.ValidationErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
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
