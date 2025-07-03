package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.LocalityType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LocationResponseDTO {
	
	private UUID id;
	
	private String name;
	
	private LocalityType type;
	
	private String pincode;
	
	private String subdistrict;
	
	private String district;
	
	private String state;
	
	private String country;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
