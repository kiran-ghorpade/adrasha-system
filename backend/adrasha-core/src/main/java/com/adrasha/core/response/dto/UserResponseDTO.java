package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class UserResponseDTO {
	
	private UUID id;
	
	private Name name;

	private UUID healthCenterId;
	
	private String adharNumber;
	
	private Set<Role> roles;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
