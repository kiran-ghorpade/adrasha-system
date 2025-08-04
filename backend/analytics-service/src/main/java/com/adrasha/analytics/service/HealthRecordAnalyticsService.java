package com.adrasha.analytics.service;

import org.springframework.stereotype.Service;

import com.adrasha.core.dto.event.HealthRecordCreatedEvent;
import com.adrasha.core.dto.event.HealthRecordDeletedEvent;
import com.adrasha.core.dto.event.HealthRecordUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthRecordAnalyticsService {

	private final NCDCountService ncdCountService;

	public void handleMemberCreated(HealthRecordCreatedEvent event) {
		ncdCountService.increment(event.getNcd(), event.getAshaId(), event.getCreatedAt());
	}

	public void handleMemberUpdated(HealthRecordUpdatedEvent event) {
		// 1. Decrement old values
		ncdCountService.decrement(event.getOldNCD(), event.getAshaId(), event.getCreatedAt());

		// 2. Increment new values
		ncdCountService.increment(event.getNewNCD(), event.getAshaId(), event.getCreatedAt());
	}

	public void handleMemberDeleted(HealthRecordDeletedEvent event) {
		ncdCountService.decrement(event.getNcd(), event.getAshaId(), event.getCreatedAt());
	}
}
