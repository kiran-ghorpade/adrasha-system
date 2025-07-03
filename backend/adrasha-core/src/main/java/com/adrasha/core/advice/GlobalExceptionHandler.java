package com.adrasha.core.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adrasha.core.dto.ApiError;
import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.exception.UnAuthorizedException;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponse> handleUnAuthorizedException(AuthorizationDeniedException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExistingException(AlreadyExistsException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorResponse> handleFeignException(FeignException  ex, HttpServletRequest request) {
		
	    ErrorResponse response = ErrorResponse.builder()
                .status(ex.status())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.status()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
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
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .path(request.getRequestURI())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,  HttpServletRequest request) {
		ex.printStackTrace();
		
        // Create error response
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
