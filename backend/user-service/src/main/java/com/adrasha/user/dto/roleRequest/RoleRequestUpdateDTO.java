package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.Role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequestUpdateDTO {

	@Valid
	private Name name;
	
	@NotNull
	private Role role;
	
	@NotNull
	private UUID healthCenter;
}
