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

import com.adrasha.core.dto.filter.MemberDataFilterDTO;
import com.adrasha.core.dto.response.MemberDataResponseDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;
import com.adrasha.core.utils.PaginationUtils;
import com.adrasha.reports.client.MemberDataClient;
import com.adrasha.reports.client.UserDataClient;

@Service
public class MemberAnalyticsService {

	@Autowired
	private MemberDataClient memberDataClient;
	
	@Autowired
	private UserDataClient userDataClient;

	private long getCount(MemberDataFilterDTO filterDTO) {
		return memberDataClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	private MemberDataFilterDTO getAshaCriteria() {
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		return MemberDataFilterDTO.builder()
				.ashaId(user.getId())
				.build();
	}
	
	public long getTotalMembers() {
		
		
		return this.getCount(this.getAshaCriteria());
	}

	public double getAverageMembersPerFamily() {
		long totalMembers = 0;
		Set<UUID> uniqueFamilyIds = new HashSet<>();

		List<MemberDataResponseDTO> data = PaginationUtils.getAllRecords(memberDataClient::getAll, this.getAshaCriteria(), null);
		
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
		
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		Map<Gender, Long> result = new HashMap<>();

		for(Gender gender : Gender.values()) {
			
			long count = this.getCount(
						MemberDataFilterDTO.builder()
						.ashaId(user.getId())
						.gender(gender)
						.build()
					);
			
			result.put(gender, count);
		}
		
		return result;
	}
	
	public Map<AgeGroup, Long> getAgeDistribution(){
	
		return PaginationUtils.getAllRecords(memberDataClient::getAll, this.getAshaCriteria(), null)
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
