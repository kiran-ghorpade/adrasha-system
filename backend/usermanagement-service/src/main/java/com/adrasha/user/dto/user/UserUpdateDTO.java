package com.adrasha.user.dto.user;

import java.util.UUID;

import com.adrasha.user.model.Name;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDTO {
	
	@NotNull
	private UUID id;
	
	@Valid
	private Name name;

	@NotNull
	private UUID healthCenter;
	
}
