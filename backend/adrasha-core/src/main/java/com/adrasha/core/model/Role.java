package com.adrasha.core.model;

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
