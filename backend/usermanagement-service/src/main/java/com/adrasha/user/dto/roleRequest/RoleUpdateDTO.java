package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.user.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleUpdateDTO {

	@NotNull
	private UUID userId;
	
	@NotNull
	private Role role;
}
