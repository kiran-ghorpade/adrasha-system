package com.adrasha.analytics.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.PovertyStatusCount;
import com.adrasha.analytics.repository.PovertyStatusCountRepository;
import com.adrasha.core.model.PovertyStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PovertyStatusCountService {

	private final PovertyStatusCountRepository countRepository;

	public void increment(PovertyStatus status, UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(status);

		countRepository.save(PovertyStatusCount.builder()
				.ashaId(ashaId)
				.povertyStatus(status)
				.count(lastCount+1)
				.createdAt(eventTime)
				.build());
	}
	
	public void decrement(PovertyStatus status, UUID ashaId, Instant eventTime) {

		Long lastCount = this.getLatestCount(status);

		countRepository.save(PovertyStatusCount.builder()
				.ashaId(ashaId)
				.povertyStatus(status)
				.count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(PovertyStatus povertyStatus) {
		return countRepository.findTopByPovertyStatusOrderByCreatedAtDesc(povertyStatus).getCount();
	}
}
