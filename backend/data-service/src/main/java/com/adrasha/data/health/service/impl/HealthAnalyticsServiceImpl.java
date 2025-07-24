package com.adrasha.data.health.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adrasha.data.health.repository.HealthRecordRepository;
import com.adrasha.data.health.service.HealthAnalyticsService;

@Service
public class HealthAnalyticsServiceImpl implements HealthAnalyticsService {

	private final HealthRecordRepository healthRecordRepository;

	public HealthAnalyticsServiceImpl(HealthRecordRepository healthRecordRepository) {
		this.healthRecordRepository = healthRecordRepository;
	}

	@Override
	public Long getPregnantCount() {
		return healthRecordRepository.countPregnant();
	}

	public Map<UUID, Long> getNcdCounts() {
		List<Object[]> results = healthRecordRepository.countByNcd();
		Map<UUID, Long> map = new HashMap<>();
		for (Object[] r : results) {
			UUID ncdId = (UUID) r[0];
			Long count = (Long) r[1];
			map.put(ncdId, count);
		}
		return map;
	}
}
