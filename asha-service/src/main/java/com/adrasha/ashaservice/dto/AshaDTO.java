package com.adrasha.ashaservice.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.ashaservice.model.Name;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AshaDTO {
	
	private UUID id;
	
	@NotBlank
	private Name name;
	
	@NotBlank
	private String mobileNumber;
	
	@NotBlank
	private UUID phcId;
	
	@NotBlank
	private UUID address;
	
	private long totalFamilies;
	private long population;
	private Instant createdAt;
	private Instant updatedAt;
}
