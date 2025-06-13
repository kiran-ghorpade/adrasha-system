package com.adrasha.masterdata.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DistrictDTO {

	private UUID id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private UUID state;
}
