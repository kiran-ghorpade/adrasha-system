package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum ReportType {
	
	AGE_WISE_REPORT("Age Wise Report"),
	FAMILY_HEAD_LIST("Family Head List"),
	DEMOGRAPY_REPORT("Demography Report"),
	NCD_REPORT("NCD Report"),
	PREGNANT_WOMEN_REPORT("Pregnant Women Report");
	
	private String displayName;

	private ReportType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
