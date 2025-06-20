package com.adrasha.core.response.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class FamilyResponseDTO {
	
	private UUID id;

	private UUID headMemberId;
	
	private UUID locationId;
	
	private UUID ashaId;
	
	private boolean belowPovertyLine;

}
