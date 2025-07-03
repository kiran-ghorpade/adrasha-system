package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum LocalityType {

	CITY("City"),
	VILLAGE("Village"),
	TOWN("Town"),
	HAMLET("Hamlet"); //  Vadi/Pada/Vasti
	
	private final String displayName;

	LocalityType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
