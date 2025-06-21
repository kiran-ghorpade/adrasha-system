package com.adrasha.analytics.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.client.MemberDataClient;
import com.adrasha.core.filter.dto.MemberDataFilterDTO;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;
import com.adrasha.core.response.dto.MemberDataResponseDTO;
import com.adrasha.core.utils.PaginationUtils;

@Service
public class MemberAnalyticsService {

	@Autowired
	private MemberDataClient memberDataClient;

	private long getCount(MemberDataFilterDTO filterDTO) {
		return memberDataClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	public long getTotalMembers() {
		return this.getCount(null);
	}

	public double getAverageMembersPerFamily() {
		long totalMembers = 0;
		Set<UUID> uniqueFamilyIds = new HashSet<>();

		List<MemberDataResponseDTO> data = PaginationUtils.getAllRecords(memberDataClient::getAll, null, null);
		
		totalMembers = data.size();
		
		data.stream()
				.map(MemberDataResponseDTO::getFamilyId)
				.filter(Objects::nonNull)
				.forEach(uniqueFamilyIds::add);		

		if (uniqueFamilyIds.isEmpty()) {
			return 0;
		}

		return (double) totalMembers / uniqueFamilyIds.size();
	}

	public Map<Gender, Long> getGenderDistribution() {
		Map<Gender, Long> result = new HashMap<>();

		for(Gender gender : Gender.values()) {
			
			long count = this.getCount(
						MemberDataFilterDTO.builder()
						.gender(gender)
						.build()
					);
			
			result.put(gender, count);
		}
		
		return result;
	}
	
	public Map<AgeGroup, Long> getAgeDistribution(){
	
		return PaginationUtils.getAllRecords(memberDataClient::getAll, null, null)
					.stream()
					.map(MemberDataResponseDTO::getAge)
					.filter(Objects::nonNull)
					.collect(Collectors.groupingBy(this::getAgeGroupByAge, Collectors.counting()));	
	}
	
	private AgeGroup getAgeGroupByAge(int age) {

		for (AgeGroup group : AgeGroup.values()) {
			if (age >= group.getMinAge() && age < group.getMaxAge()) {
				return group;
			}
		}

		return AgeGroup.UNKNOWN;
	}
}
