package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import lombok.Data;

@Data
public class HealthCenterResponseDTO {
	
	private UUID id;
	
	private String name;

	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private long totalFamilies;
	
	private long totalPopulation;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
