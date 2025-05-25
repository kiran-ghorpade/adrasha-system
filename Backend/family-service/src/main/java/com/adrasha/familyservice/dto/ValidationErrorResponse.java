package com.adrasha.familyservice.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ValidationErrorResponse {
		@Default
	    private boolean success=false;
		private int status;
		private String errorCode;
	    private String message;
	    private List<ApiError> errors;

		@Default
	    private Instant timestamp = Instant.now();}
