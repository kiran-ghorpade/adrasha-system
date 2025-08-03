package com.adrasha.analytics.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.analytics.model.GenderCount;
import com.adrasha.analytics.model.PovertyStatusCount;
import com.adrasha.analytics.model.PregnancyCount;
import com.adrasha.analytics.model.RoleRequestStatusCount;
import com.adrasha.analytics.repository.AgeGroupCountRepository;
import com.adrasha.analytics.repository.GenderCountRepository;
import com.adrasha.analytics.repository.PovertyStatusCountRepository;
import com.adrasha.analytics.repository.PregnancyCountRepository;
import com.adrasha.analytics.repository.RoleRequestStatusCountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsAggregatorService {
	private final AgeGroupCountRepository ageGroupCountRepository;
	private final GenderCountRepository genderCountRepository;
	private final PovertyStatusCountRepository povertyStatusCountRepository;
	private final PregnancyCountRepository pregnancyCountRepository;
	private final RoleRequestStatusCountRepository roleRequestStatusCountRepository;

	public Page<AgeGroupCount> getAgeGroupDistribution(Pageable pageable) {
		return ageGroupCountRepository.findAll(pageable);
	}
	
	public Page<GenderCount> getGenderDistribution(Pageable pageable) {
		return genderCountRepository.findAll(pageable);
	}

	public Page<PovertyStatusCount> getPovertyStatusDistribution(Pageable pageable) {
		return povertyStatusCountRepository.findAll(pageable);
	}
	
	public Page<PregnancyCount> getPregnancyDistribution(Pageable pageable) {
		return pregnancyCountRepository.findAll(pageable);
	}
	
	public Page<RoleRequestStatusCount> getRoleRequestStatusDistribution(Pageable pageable) {
		return roleRequestStatusCountRepository.findAll(pageable);
	}
}
