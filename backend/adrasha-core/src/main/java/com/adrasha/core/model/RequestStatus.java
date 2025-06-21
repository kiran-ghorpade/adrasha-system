package com.adrasha.core.model;

public enum RequestStatus {

	PENDING("Pending"),
	APPROVED("Approved"),
	REJECTED("Rejected");
	
	private String displayName;

	private RequestStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
