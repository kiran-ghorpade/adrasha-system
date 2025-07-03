package com.adrasha.auth.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class ApiError {

	private String field;
	private String message;
}
