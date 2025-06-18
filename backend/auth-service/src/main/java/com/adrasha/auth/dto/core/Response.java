package com.adrasha.auth.dto.core;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class Response<T> {
	
	@Default
	private Instant timestamp = Instant.now();
	@Default
	private boolean success = true;
	private int status;
	@Default
	private String message = "success";
	
	private T payload;
}
