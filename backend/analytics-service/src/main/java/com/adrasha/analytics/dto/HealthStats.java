package com.adrasha.analytics.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class HealthStats {

	private long totalHealthRecords;
	private long totalPregnantWomens;
	private Map<String , Integer> NcdDistribution;
}
