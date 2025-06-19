package com.adrasha.analytics.health.dto;

import java.util.Map;

import lombok.Data;

@Data
public class HealthStats {

	private long totalHealthRecords;
	private Map<String , Integer> NcdDistribution;
	private PregnancyStats pregnancyStats;
}
