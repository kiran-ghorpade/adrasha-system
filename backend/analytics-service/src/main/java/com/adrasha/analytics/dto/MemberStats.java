package com.adrasha.analytics.dto;

import java.util.Map;

import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberStats {

	private long totalMembers;
	private double averageMembersPerFamily;
	private Map<Gender, Long> genderDistribution;
	private Map<AgeGroup, Long> ageDistribution;

}
