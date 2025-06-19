package com.adrasha.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.adrasha.analytics.client.FamilyDataClient;

public class FamilyAnalyticsService {
	
	@Autowired
	private FamilyDataClient dataClient;
	
	
	public long getTotalFamiles() {
		dataClient.getAll(null, null);
		return 0;
	}

}
