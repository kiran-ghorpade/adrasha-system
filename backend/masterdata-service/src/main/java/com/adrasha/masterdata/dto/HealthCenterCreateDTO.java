package com.adrasha.masterdata.dto;

import java.util.UUID;

import com.adrasha.core.model.HealthCenterType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class HealthCenterCreateDTO {

	@NotBlank
	private String name;
	
	@NotNull
	private HealthCenterType centerType;
	
	@NotNull
	private UUID locationId;
	
	
	private Long totalFamilies;
	
	private Long totalPopulation;
}
