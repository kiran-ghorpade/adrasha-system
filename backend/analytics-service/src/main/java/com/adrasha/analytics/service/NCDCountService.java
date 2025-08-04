package com.adrasha.analytics.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.client.NCDClient;
import com.adrasha.analytics.model.NcdCount;
import com.adrasha.analytics.repository.NCDCountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NCDCountService {
	private final NCDCountRepository countRepository;
	private final NCDClient ncdclient;

	public void increment(UUID ncd , UUID ashaId, Instant eventTime) {

		String name = ncdclient.getNCDDetails(ncd).getName();
		Long lastCount = this.getLatestCount(name);

		countRepository.save(NcdCount.builder()
				.ashaId(ashaId)
				.ncdName(name)
				.count(lastCount+1)
				.createdAt(eventTime)
				.build());
	}
	
	public void decrement(UUID ncd, UUID ashaId, Instant eventTime) {
		
		String name = ncdclient.getNCDDetails(ncd).getName();
		Long lastCount = this.getLatestCount(name);

		countRepository.save(NcdCount.builder()
				.ashaId(ashaId)
				.ncdName(name)
				.count(Math.max(0, lastCount - 1))
	            .createdAt(eventTime)
	            .build());
	}

	public Long getLatestCount(String name) {
		return countRepository.findTopByNcdNameOrderByCreatedAtDesc(name).getCount();
	}
}
