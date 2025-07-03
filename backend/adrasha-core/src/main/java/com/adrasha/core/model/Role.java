package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum Role {
	SYSTEM("System"),
	USER("User"),
	ADMIN("Admin"),
	ASHA("Asha");
	
	private String displayName;

	private Role(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
