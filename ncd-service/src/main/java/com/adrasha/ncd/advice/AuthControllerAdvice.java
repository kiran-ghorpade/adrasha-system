package com.adrasha.ncd.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class AuthControllerAdvice {

	@ExceptionHandler(exception = ConstraintViolationException.class)
	public  void handleValidation() {
		
	}
}
