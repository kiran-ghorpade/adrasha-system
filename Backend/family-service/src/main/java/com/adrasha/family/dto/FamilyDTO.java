package com.adrasha.family.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyDTO {
	
	@NotNull
	private UUID address;
	
	@NotNull
	private UUID ashaId;
}
