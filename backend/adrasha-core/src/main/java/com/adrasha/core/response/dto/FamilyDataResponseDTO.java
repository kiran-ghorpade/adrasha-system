package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class FamilyDataResponseDTO {
	
	private UUID id;

	private UUID headMemberId;
		
	private UUID ashaId;
	
	private Integer houseId;
	
	private PovertyStatus povertyStatus;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
