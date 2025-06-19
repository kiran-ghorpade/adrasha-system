package com.adrasha.auth.dto.core;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class Response {
	
	@Default
	private Instant timestamp = Instant.now();
	@Default
	private boolean success = true;
	private int status;
	@Default
	private String message = "success";
}
