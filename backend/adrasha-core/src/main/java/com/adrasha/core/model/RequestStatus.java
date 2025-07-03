package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
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
