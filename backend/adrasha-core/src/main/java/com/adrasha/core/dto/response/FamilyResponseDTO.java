package com.adrasha.core.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class FamilyResponseDTO {
	
	private UUID id;

	private UUID headMemberId;
		
	private UUID ashaId;
	
	private Integer houseId;
	
	private PovertyStatus povertyStatus;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
