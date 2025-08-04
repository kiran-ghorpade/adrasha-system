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

	public Map<AgeGroup, List<AgeGroupCount>> getAgeGroupDistributionBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<AgeGroupCount> list = ageGroupCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(AgeGroupCount::getAgeGroup));
	}

	public Map<AliveStatus, List<AliveStatusCount>> getAliveStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<AliveStatusCount> list = aliveStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(AliveStatusCount::getAliveStatus));
	}

	public Map<Gender, List<GenderCount>> getGenderDistributionBetween(AnalyticsFilterDTO analyticsFilterDTO) {
		List<GenderCount> list = genderCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(GenderCount::getGender));
	}

	public Map<PovertyStatus, List<PovertyStatusCount>> getPovertyStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<PovertyStatusCount> list = povertyStatusCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(PovertyStatusCount::getPovertyStatus));
	}

	public Map<RequestStatus, List<RoleRequestStatusCount>> getRoleRequestStatusDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<RoleRequestStatusCount> list = roleRequestStatusCountRepository
				.findByCreatedAtBetweenOrderByCreatedAtAsc(analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(RoleRequestStatusCount::getStatus));
	}

	public Map<String, List<NcdCount>> getNcdDistributionBetween(
			AnalyticsFilterDTO analyticsFilterDTO) {
		List<NcdCount> list = ncdCountRepository.findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(
				analyticsFilterDTO.getUserId(), analyticsFilterDTO.getStart(), analyticsFilterDTO.getEnd());

		return list.stream().collect(Collectors.groupingBy(NcdCount::getNcdName));
	}
}
