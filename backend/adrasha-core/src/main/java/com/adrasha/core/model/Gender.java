package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum Gender {
	MALE("Male"), 
	FEMALE("Female"), 
	OTHER("Other");
	
	private final String displayName;
	
	private Gender(String displayName) {
		this.displayName = displayName;
	}
	
}