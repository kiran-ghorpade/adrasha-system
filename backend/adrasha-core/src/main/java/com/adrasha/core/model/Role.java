package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum Role {
	SYSTEM("System"),
	USER("User"),
	ADMIN("Admin"),
	ASHA("Asha");
	
	private final String displayName;

	private Role(String displayName) {
		this.displayName = displayName;
	}
}
