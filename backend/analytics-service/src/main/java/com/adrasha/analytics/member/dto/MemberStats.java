package com.adrasha.analytics.member.dto;

import java.util.Map;

import lombok.Data;

@Data
public class MemberStats {

	private long totalMembers;
	private GenderDistribution genderDistribution;
	private Map<AgeGroup, Integer> ageDistribution;

}
