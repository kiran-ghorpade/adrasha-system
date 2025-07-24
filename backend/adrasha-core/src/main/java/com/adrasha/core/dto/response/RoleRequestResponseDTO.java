package com.adrasha.core.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.RequestStatus;
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
public class RoleRequestResponseDTO {

	private UUID id;

	private Name name;
	
	private Role role;

	private UUID userId;
	
	private RequestStatus status;
	
	private UUID healthCenterId;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
