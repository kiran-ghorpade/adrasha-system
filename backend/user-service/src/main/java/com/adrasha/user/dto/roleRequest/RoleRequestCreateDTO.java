package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.Role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequestCreateDTO {
	
	@NotNull
	private UUID userId;
	
	@Valid
	private Name name;
	
	@NotNull
	private Role role;
	
	@NotNull
	private UUID healthCenter;
}
