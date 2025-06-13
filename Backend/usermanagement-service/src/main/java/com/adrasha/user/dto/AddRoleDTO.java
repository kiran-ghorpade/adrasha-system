package com.adrasha.user.dto;

import java.util.UUID;

import com.adrasha.core.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRoleDTO {

	@NotNull
	private UUID userId;
	
	@NotNull
	private Role role;
}
