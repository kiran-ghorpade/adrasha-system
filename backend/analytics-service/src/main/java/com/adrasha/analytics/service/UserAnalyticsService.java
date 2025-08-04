package com.adrasha.analytics.service;

import org.springframework.stereotype.Service;

import com.adrasha.core.dto.event.RoleRequestCreatedEvent;
import com.adrasha.core.dto.event.RoleRequestDeletedEvent;
import com.adrasha.core.dto.event.RoleRequestUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAnalyticsService {

	private final RoleRequestStatusCountService roleRequestStatusCountService;

	
	public void handleMemberCreated(RoleRequestCreatedEvent event) {
		roleRequestStatusCountService.increment(event.getStatus(), event.getCreatedAt());
	}
	
	public void handleMemberUpdated(RoleRequestUpdatedEvent event) {
	    // 1. Decrement old values
		roleRequestStatusCountService.decrement(event.getOldRequestStatus(), event.getCreatedAt());

	    // 2. Increment new values
		roleRequestStatusCountService.increment(event.getNewRequestStatus(), event.getCreatedAt());
	}

	
	public void handleMemberDeleted(RoleRequestDeletedEvent event) {
		roleRequestStatusCountService.decrement(event.getStatus(), event.getCreatedAt());
	}
}
