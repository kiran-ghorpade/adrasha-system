package com.adrasha.authservice.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
	
	@Default
	private Instant timestamp = Instant.now();
	@Default
	private boolean success = true;
	private String status;
	@Default
	private String message = "success";
	private T payload;
	
}
