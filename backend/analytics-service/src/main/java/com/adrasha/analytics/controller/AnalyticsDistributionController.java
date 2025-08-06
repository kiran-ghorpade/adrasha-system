package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.dto.AnalyticsFilterDTO;
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
@RequestMapping("/analytics/distribution")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics")
public class AnalyticsDistributionController {
	private final AnalyticsAggregatorService aggregatorService;

//
	@GetMapping("/requests")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<RequestStatus, Long> getRequestDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
		return aggregatorService.getRoleRequestStatusDistributionBetween(analyticsFilterDTO);
	}

	@GetMapping("/age")
	@PreAuthorize("hasRole('ASHA')")
	public Map<AgeGroup, Long> getAgeDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getAgeGroupDistributionBetween(analyticsFilterDTO);
	}
	
	@GetMapping("/alive")
	@PreAuthorize("hasRole('ASHA')")
	public Map<AliveStatus, Long> getAliveStatusDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getAliveStatusDistributionBetween(analyticsFilterDTO);
	}

	@GetMapping("/poverty")
	@PreAuthorize("hasRole('ASHA')")
	public Map<PovertyStatus, Long> getPovertyDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getPovertyStatusDistributionBetween(analyticsFilterDTO);
	}

	@GetMapping("/gender")
	@PreAuthorize("hasRole('ASHA')")
	public Map<Gender, Long> getGenderDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getGenderDistributionBetween(analyticsFilterDTO);
	}

	@GetMapping("/ncd")
	@PreAuthorize("hasRole('ASHA')")
	public Map<String, Long> getNCDDistribution(AnalyticsFilterDTO analyticsFilterDTO) {
        return aggregatorService.getNcdDistributionBetween(analyticsFilterDTO);
	}
}
