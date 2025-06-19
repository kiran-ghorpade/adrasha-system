package com.adrasha.masterdata.location.dto;

import com.adrasha.masterdata.model.LocalityType;

import lombok.Data;

@Data
public class LocationFilterDTO {
	
	private LocalityType type;
	
	private String pincode;
	
	private String subdistrict;
	
	private String district;
	
	private String state;
	
	private String country;
}
