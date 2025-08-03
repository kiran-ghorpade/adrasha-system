package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.service.AnalyticsAggregatorService;
import com.adrasha.core.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics")
@ApiResponses({
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
public class AnalyticsController {
//    private final AnalyticsAggregatorService aggregatorService;
//
//    @GetMapping("/asha-overview")
//    @PreAuthorize("hasRole('ASHA')")
//    public Map<String, Object> getAshaOverview() {
////        return aggregatorService.getAgeGroupDistribution();
//    }
//    
//    @GetMapping("/admin-overview")
//    @PreAuthorize("hasRole('ADMIN')")
//    public Map<String, Object> getAdminOverview() {
////        return aggregatorService.getAdminAnalyticsOverview();
//    }
}
