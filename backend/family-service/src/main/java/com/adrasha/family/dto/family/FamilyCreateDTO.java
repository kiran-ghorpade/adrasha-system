package com.adrasha.family.dto.family;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class FamilyCreateDTO {
	
	@NotNull
	private UUID address;
	
	@NotNull
	private UUID ashaId;
}
