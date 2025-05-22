package com.adrasha.core.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.core.dto.ApiError;
import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.exception.UserAlreadyExistsException;
import com.adrasha.core.exception.UserNotFoundException;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
public class GlobalExceptionHandler {
	
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
