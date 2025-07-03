package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum PovertyStatus {
	
	APL("Above Poverty Line (APL)"),
	BPL("Below Poverty Line (BPL)");
	
	private String displayName;

	private PovertyStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
