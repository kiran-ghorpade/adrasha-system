package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.core.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class RoleUpdateDTO {

	@NotNull
	private UUID userId;
	
	@NotNull
	private Role role;
}
