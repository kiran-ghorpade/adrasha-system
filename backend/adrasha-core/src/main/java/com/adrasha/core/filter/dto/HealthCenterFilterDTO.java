package com.adrasha.core.filter.dto;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class HealthCenterFilterDTO {
	
	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private Long totalFamilies;
	
	private Long totalPopulation;
}
