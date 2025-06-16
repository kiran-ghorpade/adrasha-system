package com.adrasha.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
	
	@Schema(example = "admin")
	@NotBlank(message = "{username.notblank}")
	private String username;
	
	@Schema(example = "admin@123")
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String password;
}
