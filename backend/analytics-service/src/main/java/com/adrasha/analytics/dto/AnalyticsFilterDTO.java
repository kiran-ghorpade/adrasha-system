package com.adrasha.analytics.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class AnalyticsFilterDTO {

	private UUID userId;
	private Instant start;
	private Instant end;
	
}
