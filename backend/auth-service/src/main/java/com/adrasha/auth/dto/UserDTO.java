package com.adrasha.auth.dto;

import java.util.Set;
import java.util.UUID;

import com.adrasha.auth.model.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UserDTO {
	
	private UUID id;
	private String username;
	private Set<String> roles;
	private AccountStatus status;
}
