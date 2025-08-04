package com.adrasha.analytics.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.RoleRequestStatusCount;
import com.adrasha.analytics.repository.RoleRequestStatusCountRepository;
import com.adrasha.core.model.RequestStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleRequestStatusCountService {

	private final RoleRequestStatusCountRepository countRepository;

	public void increment(RequestStatus status, Instant eventTime) {

		Long lastCount = this.getLatestCount(status);

		countRepository.save(RoleRequestStatusCount.builder()
				.status(status)
				.count(lastCount+1)
				.createdAt(eventTime)
				.build());
	}
	
	public void decrement(RequestStatus status, Instant eventTime) {

		Long lastCount = this.getLatestCount(status);

		countRepository.save(RoleRequestStatusCount.builder()
				.status(status)
				.count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(RequestStatus requestStatus) {
		return countRepository.findTopByStatusOrderByCreatedAtDesc(requestStatus).getCount();
	}
}
