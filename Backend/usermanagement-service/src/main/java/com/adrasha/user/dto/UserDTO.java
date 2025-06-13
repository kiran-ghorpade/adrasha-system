package com.adrasha.user.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.user.model.Name;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	
	private UUID id;
	
	@NotNull
	private Name name;
	
	@NotNull
	private UUID userId;
	
	@NotBlank
	@Size(min = 10)
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
