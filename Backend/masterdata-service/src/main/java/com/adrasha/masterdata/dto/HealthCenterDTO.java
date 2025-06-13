package com.adrasha.masterdata.dto;

import java.util.UUID;

import com.adrasha.masterdata.model.HealthCenterType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HealthCenterDTO {

	private UUID id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private HealthCenterType centerType;
	
	@NotNull
	private UUID localityId;
}
