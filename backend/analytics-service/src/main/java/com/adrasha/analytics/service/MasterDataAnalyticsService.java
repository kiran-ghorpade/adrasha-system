package com.adrasha.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.reports.client.HealthCenterDataClient;
import com.adrasha.reports.client.LocationClient;
import com.adrasha.reports.client.NCDClient;

@Service
public class MasterDataAnalyticsService {
	
	@Autowired
	private NCDClient ncdClient;
	
	@Autowired
	private HealthCenterDataClient healthCenterDataClient;
	
	@Autowired
	private LocationClient locationClient;

	public long getNCDCount() {
		return ncdClient.getCount(null).getOrDefault("count", 0L);
	}
	
	public long getHealthCenterCount() {
		return healthCenterDataClient.getCount(null).getOrDefault("count", 0L);
	}
	
	public long getLocationCount() {
		return locationClient.getCount(null).getOrDefault("count", 0L);
	}
}
