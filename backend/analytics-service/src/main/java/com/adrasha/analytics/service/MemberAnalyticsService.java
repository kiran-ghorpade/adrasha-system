package com.adrasha.analytics.service;

import org.springframework.stereotype.Service;

import com.adrasha.core.dto.event.MemberCreatedEvent;
import com.adrasha.core.dto.event.MemberDeletedEvent;
import com.adrasha.core.dto.event.MemberUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAnalyticsService {

	private final AliveStatusCountService aliveStatusCountService;
	private final AgeGroupCountService ageGroupCountService;
	private final GenderCountService genderCountService;

	
	public void handleMemberCreated(MemberCreatedEvent event) {
		aliveStatusCountService.increment(event.getAliveStatus(),event.getAshaId(), event.getCreatedAt());
		ageGroupCountService.increment(event.getAgeGroup(),event.getAshaId(), event.getCreatedAt());
    	genderCountService.increment(event.getGender(),event.getAshaId(), event.getCreatedAt());
	}
	
	public void handleMemberUpdated(MemberUpdatedEvent event) {
	    // 1. Decrement old values
		aliveStatusCountService.decrement(event.getOldAliveStatus(),event.getAshaId(), event.getCreatedAt());
	    ageGroupCountService.decrement(event.getOldAgeGroup(),event.getAshaId(), event.getCreatedAt());
	    genderCountService.decrement(event.getOldGender(),event.getAshaId(), event.getCreatedAt());

	    // 2. Increment new values	
	    aliveStatusCountService.increment(event.getNewAliveStatus(),event.getAshaId(), event.getCreatedAt());
	    ageGroupCountService.increment(event.getNewAgeGroup(),event.getAshaId(), event.getCreatedAt());
	    genderCountService.increment(event.getNewGender(),event.getAshaId(), event.getCreatedAt());
	}

	
	public void handleMemberDeleted(MemberDeletedEvent event) {
		aliveStatusCountService.decrement(event.getAliveStatus(),event.getAshaId(), event.getCreatedAt());
		ageGroupCountService.decrement(event.getAgeGroup(),event.getAshaId(), event.getCreatedAt());
    	genderCountService.decrement(event.getGender(),event.getAshaId(), event.getCreatedAt());
	}
}
