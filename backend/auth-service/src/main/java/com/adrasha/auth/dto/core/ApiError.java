package com.adrasha.auth.dto.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

	private String field;
	private String message;
}
