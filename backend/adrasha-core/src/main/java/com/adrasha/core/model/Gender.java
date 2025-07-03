package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum Gender {
	MALE("Male"), 
	FEMALE("Female"), 
	OTHER("Other");
	
	private String displayName;
	
	private Gender(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}