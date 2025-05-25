package com.adrasha.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {
	
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String oldPassword;
	
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String newPassword;
}
