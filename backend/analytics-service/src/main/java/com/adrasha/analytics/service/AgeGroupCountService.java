package com.adrasha.analytics.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.analytics.repository.AgeGroupCountRepository;
import com.adrasha.core.model.AgeGroup;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgeGroupCountService {
	private final AgeGroupCountRepository ageGroupCountRepository;

	public void increment(AgeGroup group, UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(group);

		ageGroupCountRepository.save(AgeGroupCount.builder()
				.ashaId(ashaId)
				.ageGroup(group)
				.count(lastCount+1)
				.createdAt(eventTime)
				.build());
	}
	
	public void decrement(AgeGroup group, UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(group);

		ageGroupCountRepository.save(AgeGroupCount.builder()
				.ashaId(ashaId)
				.ageGroup( group)
				.count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(AgeGroup group) {
//		Instant lastCreatedAt = ageGroupCountRepository.getLastRecord();
		return ageGroupCountRepository.findTopByAgeGroupOrderByCreatedAtDesc(group).getCount();
	}
}
