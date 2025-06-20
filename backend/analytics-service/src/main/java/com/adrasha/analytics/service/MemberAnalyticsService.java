package com.adrasha.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.adrasha.analytics.client.MemberDataClient;
import com.adrasha.core.response.dto.MemberResponseDTO;

public class MemberAnalyticsService {
	
	@Autowired
	private MemberDataClient memberDataClient;
	
	public long getAverageMemberPerFamily() {
		Page<MemberResponseDTO> memberPage = memberDataClient.getAll(null, null);
		
		memberPage.getTotalElements();
		
		return 0;
	}

}
