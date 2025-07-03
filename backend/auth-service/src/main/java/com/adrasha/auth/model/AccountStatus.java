package com.adrasha.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum AccountStatus {
	
	PENDING,
	APPROVED,
	REJECTED,
	BLOCKED

}
