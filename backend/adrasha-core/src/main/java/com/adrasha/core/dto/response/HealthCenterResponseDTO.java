package com.adrasha.core.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class HealthCenterResponseDTO {
	
	private UUID id;
	
	private String name;

	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private Long totalFamilies;
	
	private Long totalPopulation;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
