package com.adrasha.authservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
	
	private UUID id;
	private String username;
	private String role;
	private LocalDateTime lastPasswordReset;
	private boolean isActive;
}
