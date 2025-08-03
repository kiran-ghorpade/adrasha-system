package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum PovertyStatus {
	
	APL("Above Poverty Line (APL)"),
	BPL("Below Poverty Line (BPL)");
	
	private final String displayName;

	private PovertyStatus(String displayName) {
		this.displayName = displayName;
	}
}
