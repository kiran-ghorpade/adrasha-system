package com.adrasha.monolithic.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
