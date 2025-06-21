package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class NCDResponseDTO {
	
	private UUID id;

	private String name;
	
	private String description;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
