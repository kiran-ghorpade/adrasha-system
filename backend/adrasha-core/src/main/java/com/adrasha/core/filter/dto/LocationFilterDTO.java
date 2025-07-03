package com.adrasha.core.filter.dto;

import com.adrasha.core.model.LocalityType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LocationFilterDTO {
	
	private LocalityType type;
	
	private String pincode;
	
	private String subdistrict;
	
	private String district;
	
	private String state;
	
	private String country;
}
