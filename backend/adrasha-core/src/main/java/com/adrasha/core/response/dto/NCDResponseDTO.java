package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class NCDResponseDTO {
	
	private UUID id;

	private String name;
	
	private String description;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
