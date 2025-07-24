package com.adrasha.core.dto.filter;

import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class FamilyDataFilterDTO {
	
	private UUID ashaId;
	private PovertyStatus povertyStatus;

}
