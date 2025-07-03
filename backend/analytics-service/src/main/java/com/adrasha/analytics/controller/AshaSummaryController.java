package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.dto.FamilyStats;
import com.adrasha.analytics.dto.MemberStats;
import com.adrasha.analytics.service.FamilyAnalyticsService;
import com.adrasha.analytics.service.MemberAnalyticsService;
import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics")
@ApiResponses({
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
@PreAuthorize("hasRole('ASHA')")
public class AshaSummaryController {

	@Autowired
	private MemberAnalyticsService memberAnalyticsService;

	@Autowired
	private FamilyAnalyticsService familyAnalyticsService;

	@GetMapping("/family/stats")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyStats.class)))
	public FamilyStats getFamilyStats() {
		long totalMembers = familyAnalyticsService.getTotalFamiliesCountByAsha();
		Map<PovertyStatus, Long> povertyStats = familyAnalyticsService.getPovertyStats();

		return FamilyStats.builder().totalFamilies(totalMembers).povertyStats(povertyStats).build();
	}

	@GetMapping("/member/stats")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberStats.class)))
	public MemberStats getMemberStats() {
		long totalMembers = memberAnalyticsService.getTotalMembers();
		double avgMembers = memberAnalyticsService.getAverageMembersPerFamily();
		Map<Gender, Long> genderDistribution = memberAnalyticsService.getGenderDistribution();
		Map<AgeGroup, Long> ageDistribution = memberAnalyticsService.getAgeDistribution();

		return MemberStats.builder().totalMembers(totalMembers).averageMembersPerFamily(avgMembers)
				.genderDistribution(genderDistribution).ageDistribution(ageDistribution).build();
	}
}
