package com.adrasha.masterdata.dto;

import java.util.UUID;

import com.adrasha.masterdata.model.LocalityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocalityDTO {

	private UUID id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private LocalityType type;
	
	@NotBlank
	private String pincode;
	
	@NotNull
	private UUID subdistrict;
}
