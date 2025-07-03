package com.adrasha.analytics.dto;

import java.util.Map;

import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class MemberStats {

	private long totalMembers;
	private double averageMembersPerFamily;
	private Map<Gender, Long> genderDistribution;
	private Map<AgeGroup, Long> ageDistribution;

}
