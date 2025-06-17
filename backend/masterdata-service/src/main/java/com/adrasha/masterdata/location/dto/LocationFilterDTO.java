package com.adrasha.masterdata.location.dto;

import java.util.UUID;

import com.adrasha.masterdata.location.model.LocalityType;

import lombok.Data;

@Data
public class LocationFilterDTO {

	private LocalityType type;
	
	private String pincode;

	private UUID subdistrict;
}
