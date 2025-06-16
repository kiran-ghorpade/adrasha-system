package com.adrasha.auth.dto;

import java.util.UUID;

import com.adrasha.auth.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddRoleDTO {

	@NotNull
	private UUID userId;
	
	@NotNull
	private Role role;
}
