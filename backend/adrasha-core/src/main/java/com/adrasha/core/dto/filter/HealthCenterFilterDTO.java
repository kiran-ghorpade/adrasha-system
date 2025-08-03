package com.adrasha.core.dto.filter;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class HealthCenterFilterDTO {
	
	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private Long totalFamilies;
	
	private Long totalPopulation;
}
