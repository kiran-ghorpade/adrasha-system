package com.adrasha.data.family.dto;

import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class FamilyCreateDTO {

	@NotNull
	private UUID locationId;
	
	@NotNull
	private UUID ashaId;

	@NotNull
	private PovertyStatus povertyStatus;

}
