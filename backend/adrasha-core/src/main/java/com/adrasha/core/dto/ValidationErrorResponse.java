package com.adrasha.core.dto;

import java.time.Instant;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@Schema
public class ValidationErrorResponse {
		@Default
	    private boolean success=false;
		private int status;
		private String error;
	    private String message;
	    private String path;
	    private List<ApiError> errors;

		@Default
	    private Instant timestamp = Instant.now();}
