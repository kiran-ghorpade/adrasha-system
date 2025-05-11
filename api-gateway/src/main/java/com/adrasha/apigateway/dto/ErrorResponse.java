package com.adrasha.apigateway.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	@Default
    private boolean success= false;
	private int status;
	private String errorCode;
	private String message;
	@Default
    private Instant timestamp = Instant.now();
}
