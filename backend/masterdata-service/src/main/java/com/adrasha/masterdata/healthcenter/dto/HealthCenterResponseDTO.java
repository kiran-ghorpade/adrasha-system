package com.adrasha.masterdata.healthcenter.dto;

import java.util.UUID;

import com.adrasha.masterdata.model.HealthCenterType;

import lombok.Data;

@Data
public class HealthCenterResponseDTO {
	
	private UUID id;
	
	private String name;

	private HealthCenterType centerType;
	
	private UUID locationId;
}
