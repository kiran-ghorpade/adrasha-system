package com.adrasha.analytics.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.GenderCount;
import com.adrasha.analytics.repository.GenderCountRepository;
import com.adrasha.core.model.Gender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenderCountService {
	private final GenderCountRepository countRepository;

	public void increment(Gender gender , UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(gender);

		countRepository.save(GenderCount.builder()
				.ashaId(ashaId)
				.gender(gender)
				.count(lastCount+1)
				.createdAt(eventTime)
				.build());
	}
	
	public void decrement(Gender gender, UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(gender);

		countRepository.save(GenderCount.builder()
				.ashaId(ashaId)
				.gender(gender)
				.count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(Gender gender) {
		return countRepository.findTopByGenderOrderByCreatedAtDesc(gender).getCount();
	}
}
