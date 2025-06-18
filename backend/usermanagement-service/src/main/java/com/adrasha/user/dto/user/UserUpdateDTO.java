package com.adrasha.user.dto.user;

import java.util.UUID;

import com.adrasha.user.model.Name;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
	
	@NotNull
	private UUID id;
	
	@Valid
	private Name name;

	@NotNull
	private UUID healthCenter;
	
	@Size(min=12, max=12)
	private String adharNumber;
}
