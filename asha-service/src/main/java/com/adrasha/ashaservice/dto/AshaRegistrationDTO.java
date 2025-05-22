package com.adrasha.ashaservice.dto;

import java.util.UUID;

import com.adrasha.ashaservice.model.Name;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AshaRegistrationDTO {

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
}
