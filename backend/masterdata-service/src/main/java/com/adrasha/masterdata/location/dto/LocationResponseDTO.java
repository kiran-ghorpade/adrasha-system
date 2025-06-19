package com.adrasha.masterdata.location.dto;

import java.util.UUID;

import com.adrasha.masterdata.model.LocalityType;

import lombok.Data;

@Data
public class LocationResponseDTO {
	
	private UUID id;
	
	private String name;
	
	private LocalityType type;
	
	private String pincode;
	
	private String subdistrict;
	
	private String district;
	
	private String state;
	
	private String country;
}
