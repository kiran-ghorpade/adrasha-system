package com.adrasha.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public enum ReportType {
	
	AGE_WISE_REPORT("Age Wise Report"),
	FAMILY_HEAD_LIST("Family Head List"),
	DEMOGRAPY_REPORT("Demography Report"),
	NCD_REPORT("NCD Report"),
	PREGNANT_WOMEN_REPORT("Pregnant Women Report");
	
	private final String displayName;

	private ReportType(String displayName) {
		this.displayName = displayName;
	}
	
}
