package com.adrasha.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

	@Schema(example = "admin")
	@NotBlank(message = "{username.notblank}")
	@Size(max = 50, message = "{username.size}")
	private String username;
	
	@Schema(example = "admin@123")
	@NotBlank(message = "password.notblank}")
	@Size(min=8, max = 100, message = "{password.size}")
	private String password;
}
