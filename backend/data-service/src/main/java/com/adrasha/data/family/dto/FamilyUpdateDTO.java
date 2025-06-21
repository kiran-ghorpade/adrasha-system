package com.adrasha.data.family.dto;

import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyUpdateDTO {
	
	@NotNull
	private UUID headMemberId;
	
	@NotNull
	private UUID locationId;

	@NotNull
	private UUID ashaId;

	private PovertyStatus povertyStatus;

}
