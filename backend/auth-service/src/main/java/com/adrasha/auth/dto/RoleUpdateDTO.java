package com.adrasha.auth.dto;

import java.util.UUID;

import com.adrasha.auth.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class RoleUpdateDTO {

	@NotNull
	private UUID userId;
	
	@NotNull
	private Role role;
}
