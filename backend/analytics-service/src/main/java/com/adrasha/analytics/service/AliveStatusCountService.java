package com.adrasha.analytics.service;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.AliveStatusCount;
import com.adrasha.analytics.repository.AliveStatusCountRepository;
import com.adrasha.core.model.AliveStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AliveStatusCountService {
	private final AliveStatusCountRepository aliveStatusCountRepository;

	public void increment(AliveStatus status, UUID ashaId, Instant eventTime) {
	    Long lastCount = getLatestCount(status);
	    aliveStatusCountRepository.save(AliveStatusCount.builder()
	    		.ashaId(ashaId)
	            .aliveStatus(status)
	            .count(lastCount + 1)
	            .createdAt(eventTime)
	            .build());
	}
	
	public void decrement(AliveStatus status, UUID ashaId, Instant eventTime) {
	    Long lastCount = getLatestCount(status);
	    aliveStatusCountRepository.save(AliveStatusCount.builder()
	    		.ashaId(ashaId)
	            .aliveStatus(status)
	            .count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(AliveStatus aliveStatus) {
	    return Optional.ofNullable(aliveStatusCountRepository.findTopByAliveStatusOrderByCreatedAtDesc(aliveStatus))
	            .map(AliveStatusCount::getCount)
	            .orElse(0L); 	}
}
