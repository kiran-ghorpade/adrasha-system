package com.adrasha.masterdata.dto;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HealthCenterCreateDTO {

	@NotBlank
	private String name;
	
	@NotNull
	private HealthCenterType centerType;
	
	@NotNull
	private UUID locationId;
	
	
	private long totalFamilies;
	
	private long totalPopulation;
}
