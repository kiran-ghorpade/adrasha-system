package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequestResponseDTO {

	private UUID id;

	private Name name;
	
	private Role role;

	private UUID userId;
	
	private RequestStatus status;
	
	private UUID healthCenter;
}
