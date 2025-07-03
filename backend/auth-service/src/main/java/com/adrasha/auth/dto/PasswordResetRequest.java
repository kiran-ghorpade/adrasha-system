package com.adrasha.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class PasswordResetRequest {
	
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String oldPassword;
	
	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, message = "{password.size}")
	private String newPassword;
}
