package com.adrasha.ashaservice.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.ashaservice.model.Name;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	
	@NotNull
	private Name name;
	
	@NotNull
	private UUID userId;
	
	@NotBlank
	private String mobileNumber;
	
	@NotNull
	private UUID phcId;
	
	@NotNull
	private UUID address;
	
	private long totalFamilies;
	private long population;
	private Instant createdAt;
	private Instant updatedAt;
}
