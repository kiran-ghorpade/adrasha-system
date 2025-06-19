package com.adrasha.masterdata.model;

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
