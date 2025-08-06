package com.adrasha.analytics.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.dto.AnalyticsFilterDTO;
import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.analytics.model.AliveStatusCount;
import com.adrasha.analytics.model.GenderCount;
import com.adrasha.analytics.model.NcdCount;
import com.adrasha.analytics.model.PovertyStatusCount;
import com.adrasha.analytics.model.RoleRequestStatusCount;
import com.adrasha.analytics.service.AnalyticsAggregatorService;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.core.model.RequestStatus;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analytics/trends")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics")
public class AnalyticsTrendsController {
	private final AnalyticsAggregatorService aggregatorService;

//
	@GetMapping("/request")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<RequestStatus, List<RoleRequestStatusCount>> getRequestTrends(AnalyticsFilterDTO analyticsFilterDTO) {
		return aggregatorService.getRoleRequestStatusTrendsBetween(analyticsFilterDTO);
	}

	@GetMapping("/age")
	@PreAuthorize("hasRole('ASHA')")
	public Map<AgeGroup, List<AgeGroupCount>> getAgeTrends(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getAgeGroupTrendsBetween(analyticsFilterDTO);
	}
	
	@GetMapping("/alive")
	@PreAuthorize("hasRole('ASHA')")
	public Map<AliveStatus, List<AliveStatusCount>> getAliveStatusTrends(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getAliveStatusTrendsBetween(analyticsFilterDTO);
	}


	@GetMapping("/poverty")
	@PreAuthorize("hasRole('ASHA')")
	public Map<PovertyStatus, List<PovertyStatusCount>> getPovertyTrends(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getPovertyStatusTrendsBetween(analyticsFilterDTO);
	}

	@GetMapping("/gender")
	@PreAuthorize("hasRole('ASHA')")
	public Map<Gender, List<GenderCount>> getGenderTrends(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getGenderTrendsBetween(analyticsFilterDTO);
	}

	@GetMapping("/ncd")
	@PreAuthorize("hasRole('ASHA')")
	public Map<String, List<NcdCount>> getNCDTrends(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getNcdTrendsBetween(analyticsFilterDTO);
	}
}
