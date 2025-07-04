package com.adrasha.core.filter.dto;

import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class FamilyDataFilterDTO {
	
	private UUID locationId;
	private UUID ashaId;
	private PovertyStatus povertyStatus;

}
