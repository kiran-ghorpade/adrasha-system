package com.adrasha.masterdata.health.dto;

import java.util.UUID;

import com.adrasha.masterdata.health.model.HealthCenterType;

import lombok.Data;

@Data
public class HealthCenterFilterDTO {

	private HealthCenterType centerType;
	private UUID locationId;
}
