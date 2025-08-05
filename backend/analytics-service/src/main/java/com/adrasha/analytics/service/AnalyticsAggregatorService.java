package com.adrasha.analytics.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.dto.AnalyticsFilterDTO;
import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.analytics.model.AliveStatusCount;
import com.adrasha.analytics.model.GenderCount;
import com.adrasha.analytics.model.NcdCount;
import com.adrasha.analytics.model.PovertyStatusCount;
import com.adrasha.analytics.model.RoleRequestStatusCount;
import com.adrasha.analytics.repository.AgeGroupCountRepository;
import com.adrasha.analytics.repository.AliveStatusCountRepository;
import com.adrasha.analytics.repository.GenderCountRepository;
import com.adrasha.analytics.repository.NCDCountRepository;
import com.adrasha.analytics.repository.PovertyStatusCountRepository;
import com.adrasha.analytics.repository.RoleRequestStatusCountRepository;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.core.model.RequestStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsAggregatorService {
	
	private final AgeGroupCountRepository ageGroupCountRepository;
	private final AliveStatusCountRepository aliveStatusCountRepository;
	private final GenderCountRepository genderCountRepository;
	private final PovertyStatusCountRepository povertyStatusCountRepository;
	private final RoleRequestStatusCountRepository roleRequestStatusCountRepository;
	private final NCDCountRepository ncdCountRepository;

	// AgeGroup
	public Map<AgeGroup, List<AgeGroupCount>> getAgeGroupTrendsBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<AgeGroupCount> list = ageGroupCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(AgeGroupCount::getAgeGroup));
	}

	public Map<AgeGroup, Long> getAgeGroupDistributionBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<AgeGroupCount> list = ageGroupCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(
				Collectors.groupingBy(AgeGroupCount::getAgeGroup, Collectors.summingLong(AgeGroupCount::getCount)));
	}

	// Alive Status
	public Map<AliveStatus, List<AliveStatusCount>> getAliveStatusTrendsBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<AliveStatusCount> list = aliveStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(AliveStatusCount::getAliveStatus));
	}
	
	public Map<AliveStatus, Long> getAliveStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<AliveStatusCount> list = aliveStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(AliveStatusCount::getAliveStatus, Collectors.summingLong(AliveStatusCount::getCount)));
	}

	// Gender
	public Map<Gender, List<GenderCount>> getGenderTrendsBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<GenderCount> list = genderCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(GenderCount::getGender));
	}
	
	public Map<Gender, Long> getGenderDistributionBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<GenderCount> list = genderCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(GenderCount::getGender, Collectors.summingLong(GenderCount::getCount)));
	}


	// Poverty
	public Map<PovertyStatus, List<PovertyStatusCount>> getPovertyStatusTrendsBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<PovertyStatusCount> list = povertyStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(PovertyStatusCount::getPovertyStatus));
	}
	
	public Map<PovertyStatus, Long> getPovertyStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<PovertyStatusCount> list = povertyStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(PovertyStatusCount::getPovertyStatus, Collectors.summingLong(PovertyStatusCount::getCount)));
	}

	// Request Status
	public Map<RequestStatus, List<RoleRequestStatusCount>> getRoleRequestStatusTrendsBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<RoleRequestStatusCount> list = roleRequestStatusCountRepository
				.findByCreatedAtBetweenOrderByCreatedAtAsc(analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(RoleRequestStatusCount::getStatus));
	}
	
	public Map<RequestStatus, Long> getRoleRequestStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<RoleRequestStatusCount> list = roleRequestStatusCountRepository
				.findByCreatedAtBetweenOrderByCreatedAtAsc(analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(RoleRequestStatusCount::getStatus, Collectors.summingLong(RoleRequestStatusCount::getCount)));
	}

	// NCD
	public Map<String, List<NcdCount>> getNcdTrendsBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<NcdCount> list = ncdCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(NcdCount::getNcdName));
	}
	
	public Map<String, Long> getNcdDistributionBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<NcdCount> list = ncdCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(NcdCount::getNcdName, Collectors.summingLong(NcdCount::getCount)));
	}
}
