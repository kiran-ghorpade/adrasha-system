package com.adrasha.auth.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
	
	private UUID id;
	private String username;
	private List<String> roles;
	private String status;
}
