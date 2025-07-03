package com.adrasha.user.dto.user;

import java.util.UUID;

import com.adrasha.core.model.Name;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class UserUpdateDTO {
	
	@NotNull
	private UUID id;
	
	@Valid
	private Name name;

	@NotNull
	private UUID healthCenterId;
	
	@Size(min=12, max=12)
	private String adharNumber;
}
