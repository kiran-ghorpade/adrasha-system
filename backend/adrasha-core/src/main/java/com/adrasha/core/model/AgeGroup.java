package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum AgeGroup {
	
	INFANT("Infant", 0, 1),
	CHILD("Child", 1, 12),
	TEEN("Teen", 12, 18),
	ADULT("Adult", 18, 35),
	MID_AGED_ADULT("Mid-age Adult", 35, 60),
	SENIOR("Senior", 60, 200),
	UNKNOWN("Unknown", -1, -1);
	
	private String displayName;
	private int minAge;
	private int maxAge;
	
	private AgeGroup(String displayName, int minAge, int maxAge) {
		this.displayName = displayName;
		this.minAge = minAge;
		this.maxAge = maxAge;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	
	
}
