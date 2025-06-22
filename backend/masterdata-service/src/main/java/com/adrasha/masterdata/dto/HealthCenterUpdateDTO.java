package com.adrasha.masterdata.dto;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HealthCenterUpdateDTO {
	
	@NotBlank
	private String name;
	
	@NotNull
	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private Long totalFamilies;
	
	private Long totalPopulation;
}
