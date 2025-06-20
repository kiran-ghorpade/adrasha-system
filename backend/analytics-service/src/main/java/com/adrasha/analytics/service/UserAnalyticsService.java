package com.adrasha.analytics.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.adrasha.analytics.client.HealthCenterDataClient;
import com.adrasha.analytics.client.UserDataClient;
import com.adrasha.core.response.dto.HealthCenterResponseDTO;
import com.adrasha.core.response.dto.UserResponseDTO;

public class UserAnalyticsService {

	@Autowired
	private UserDataClient userDataClient;

	@Autowired
	private HealthCenterDataClient healthCenterDataClient;

	public long getTotalFamiles() {
		UserResponseDTO dataPage = userDataClient.getCurrentUserDetails();
		HealthCenterResponseDTO healthInfo = healthCenterDataClient.get(dataPage.getHealthCenterId());
		return healthInfo.getTotalFamilies();
	}

	public long getTotalUsers() {
		Page<UserResponseDTO> dataPage = userDataClient.getAll(null, null);
		return dataPage.getTotalElements();
	}

	public Map<String, Integer> getRoleDistribution() {
		return userDataClient.getRoleDistribution();
	}
}
