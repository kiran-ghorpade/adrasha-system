package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.dto.MasterdataStats;
import com.adrasha.analytics.dto.UserStats;
import com.adrasha.analytics.service.MasterDataAnalyticsService;
import com.adrasha.analytics.service.UserAnalyticsService;
import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.model.Role;
import com.adrasha.core.page.dto.FamilyPageResponseDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics Summary")
@ApiResponses({
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
@PreAuthorize("hasRole('ADMIN')")
public class AdminSummaryController {

	@Autowired
	private UserAnalyticsService userAnalyticsService;

	@Autowired
	private MasterDataAnalyticsService masterDataAnalyticsService;

	@GetMapping("/user/stats")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
	public UserStats getUserStats() {
		Map<Role, Long> roleDistribution = userAnalyticsService.getRoleDistribution();
		long totalUsers = userAnalyticsService.getTotalUsers();

		return UserStats.builder().totalUsers(totalUsers).roleDistribution(roleDistribution).build();
	}

	@GetMapping("/masterdata/stats")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
	public MasterdataStats getMasterDataStats() {
		long totalHealthCenters = masterDataAnalyticsService.getHealthCenterCount();
		long totalLocations = masterDataAnalyticsService.getLocationCount();
		long totalNCD = masterDataAnalyticsService.getNCDCount();

		return MasterdataStats.builder().totalHealthCenters(totalHealthCenters).totalLocations(totalLocations)
				.totalNCD(totalNCD).build();
	}
}
