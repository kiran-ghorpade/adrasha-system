package com.adrasha.masterdata.dto;

import com.adrasha.core.model.LocalityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationUpdateDTO {

	@NotBlank
	private String name;
	
	@NotNull
	private LocalityType type;
	
	@NotBlank
	private String pincode;
	
	@NotBlank
	private String subdistrict;
	
	@NotBlank
	private String district;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String country;
}
