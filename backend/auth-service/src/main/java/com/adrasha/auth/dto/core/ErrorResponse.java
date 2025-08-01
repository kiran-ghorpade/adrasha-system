package com.adrasha.auth.dto.core;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@Schema
public class ErrorResponse {
	@Default
    private boolean success= false;
	private int status;
	private String error;
	private String message;
	private String path;
	@Default
    private Instant timestamp = Instant.now();
}
