package com.adrasha.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.adrasha.analytics.client.FamilyDataClient;
import com.adrasha.analytics.family.dto.PovertyStatus;
import com.adrasha.analytics.family.dto.RegistrationStatus;
import com.adrasha.core.response.dto.FamilyResponseDTO;

public class FamilyAnalyticsService {
	
	@Autowired
	private FamilyDataClient dataClient;
	
	public long getTotalRegisteredFamiles() {
		 Page<FamilyResponseDTO> dataPage = dataClient.getAll(null, null);
		return dataPage.getTotalElements();
	}
	
    private double averageMembersPerFamily;
    private PovertyStatus povertyStats;
   	private RegistrationStatus registrationStats;
}
