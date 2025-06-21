package com.adrasha.analytics.dto;

import java.util.Map;

import lombok.Data;

@Data
public class HealthStats {

	private long totalHealthRecords;
	private long totalPregnantWomens;
	private Map<String , Integer> NcdDistribution;
}
