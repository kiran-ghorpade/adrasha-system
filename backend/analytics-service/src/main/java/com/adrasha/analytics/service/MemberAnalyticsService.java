package com.adrasha.analytics.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.analytics.repository.AgeGroupCountRepository;
import com.adrasha.analytics.repository.AliveStatusCountRepository;
import com.adrasha.analytics.repository.GenderCountRepository;
import com.adrasha.core.dto.event.MemberCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAnalyticsService {

	private final AgeGroupCountRepository ageGroupCountRepository;
	private final AliveStatusCountRepository aliveStatusCountRepository;
	private final GenderCountRepository genderCountRepository;
	
	public void createNewMemberRecord(MemberCreatedEvent event) {
//			
//    	Long lastCount = this.getLatestCount();
//
//    	ageGroupCountRepository.save(AgeGroupCount.builder()
//					.ageGroup(event.getAgeGroup())
//					.count(lastCount++)
//					.createdAt(event.getCreatedAt())
//					.build()
//					);
	}
	
//	public Long getLatestCount() {
//		Instant lastCreatedAt = ageGroupCountRepository.getLastRecord();
//		return ageGroupCountRepository.getOneByCreatedAt(lastCreatedAt);
//	}
}
