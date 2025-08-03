package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum RequestStatus {

	PENDING("Pending"),
	APPROVED("Approved"),
	REJECTED("Rejected");
	
	private final String displayName;

	private RequestStatus(String displayName) {
		this.displayName = displayName;
	}
}
