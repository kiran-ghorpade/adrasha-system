package com.adrasha.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank(message = "{username.notblank}")
	private String username;
	
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String password;
}
