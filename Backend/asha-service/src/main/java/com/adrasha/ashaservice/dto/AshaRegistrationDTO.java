package com.adrasha.ashaservice.dto;

import java.util.UUID;

import com.adrasha.ashaservice.model.Name;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AshaRegistrationDTO {

	@Schema(description = "ASHA's Name", example = "ASHA_Name", requiredMode = RequiredMode.REQUIRED)
	@NotNull
	private Name name;

	@Schema(description = "ASHA's Associated  User unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
	@NotNull
	private UUID userId;

	@Schema(description = "Asha's unique mobile number", example = "9876543210")
	@NotBlank
	private String mobileNumber;

	@Schema(description = "PHC unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
	@NotNull
	private UUID phcId;

	@Schema(description = "Asha's address identifier", example = "123e4567-e89b-12d3-a456-426614174000")
	@NotNull
	private UUID address;

	@Schema(description = "Total Families assigned to Asha", example = "200")
	private long totalFamilies;

	@Schema(description = "Total Population assigned to Asha", example = "1020")
	private long population;
}
