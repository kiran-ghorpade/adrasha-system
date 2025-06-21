package com.adrasha.data.family.dto;

import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyCreateDTO {

	@NotNull
	private UUID locationId;
	
	@NotNull
	private UUID ashaId;

	@NotNull
	private PovertyStatus povertyStatus;

}
