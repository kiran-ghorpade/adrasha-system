package com.adrasha.analytics.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.filter.FamilyDataFilterDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.reports.client.FamilyDataClient;
import com.adrasha.reports.client.UserDataClient;

@Service
public class FamilyAnalyticsService {
	
	@Autowired
	private FamilyDataClient familyDataClient;
	
	@Autowired
	private UserDataClient userDataClient;
	

	private long getCount(FamilyDataFilterDTO filterDTO) {
		return familyDataClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	public long getTotalFamiliesCountByAsha() {
		
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		return this.getCount(
					FamilyDataFilterDTO.builder()
					.ashaId(user.getId())
					.build()
				);
	}
	
	public Map<PovertyStatus, Long> getPovertyStats(){
		
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		Map<PovertyStatus, Long> result = new HashMap<>();

		for (PovertyStatus status : PovertyStatus.values()) {
			long count = this.getCount( 
					 FamilyDataFilterDTO.builder()
					.ashaId(user.getId())
					.povertyStatus(status)
					.build()
					);
			result.put(status, count);
		}

		return result;
	}
}
