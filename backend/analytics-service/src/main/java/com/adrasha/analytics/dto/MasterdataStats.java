package com.adrasha.analytics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class MasterdataStats {

	private long totalLocations;
	private long totalHealthCenters;
	private long totalNCD;
}
