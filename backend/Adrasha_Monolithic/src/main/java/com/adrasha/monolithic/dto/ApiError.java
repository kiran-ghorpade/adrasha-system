package com.adrasha.monolithic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

	private String field;
	private String message;
}
