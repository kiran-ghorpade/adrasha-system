package com.adrasha.analytics.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.client.FamilyDataClient;
import com.adrasha.core.filter.dto.FamilyDataFilterDTO;
import com.adrasha.core.model.PovertyStatus;

@Service
public class FamilyAnalyticsService {
	
	@Autowired
	private FamilyDataClient familyDataClient;
	

	private long getCount(FamilyDataFilterDTO filterDTO) {
		return familyDataClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	public long getTotalFamiliesCount() {
		return this.getCount(null);
	}
	
	public Map<PovertyStatus, Long> getPovertyStats(){
		
		Map<PovertyStatus, Long> result = new HashMap<>();

		for (PovertyStatus status : PovertyStatus.values()) {
			long count = this.getCount( 
					 FamilyDataFilterDTO.builder()
					.povertyStatus(status)
					.build()
					);
			result.put(status, count);
		}

		return result;
	}
}
