package com.adrasha.core.filter.dto;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import lombok.Data;

@Data
public class HealthCenterFilterDTO {
	
	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private Long totalFamilies;
	
	private Long totalPopulation;
}
