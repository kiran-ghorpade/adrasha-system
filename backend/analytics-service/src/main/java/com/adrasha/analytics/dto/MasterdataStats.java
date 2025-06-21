package com.adrasha.analytics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MasterdataStats {

	private long totalLocations;
	private long totalHealthCenters;
	private long totalNCD;
}
