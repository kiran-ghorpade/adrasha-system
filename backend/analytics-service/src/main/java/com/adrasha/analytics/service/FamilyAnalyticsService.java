package com.adrasha.analytics.service;

import org.springframework.stereotype.Service;

import com.adrasha.core.dto.event.FamilyCreatedEvent;
import com.adrasha.core.dto.event.FamilyDeletedEvent;
import com.adrasha.core.dto.event.FamilyUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyAnalyticsService {

	private final PovertyStatusCountService povertyStatusCountService;

	
	public void handleMemberCreated(FamilyCreatedEvent event) {
		povertyStatusCountService.increment(event.getPovertyStatus(), event.getAshaId(), event.getCreatedAt());
	}
	
	public void handleMemberUpdated(FamilyUpdatedEvent event) {
		// 1. Decrement old values
		povertyStatusCountService.decrement(event.getOldPovertyStatus(), event.getAshaId(), event.getCreatedAt());

		// 2. Increment new values
		povertyStatusCountService.increment(event.getNewPovertyStatus(), event.getAshaId(), event.getCreatedAt());
	}

	
	public void handleMemberDeleted(FamilyDeletedEvent event) {
		povertyStatusCountService.decrement(event.getPovertyStatus(), event.getAshaId(), event.getCreatedAt());
	}
}
