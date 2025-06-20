package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.family.dto.FamilyStats;
import com.adrasha.analytics.service.UserAnalyticsService;
import com.adrasha.analytics.user.dto.UserStats;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics Summary")
@PreAuthorize("hasRole('ASHA')")
public class SummaryController {
	
	@Autowired
	private UserAnalyticsService userAnalyticsService;

    @GetMapping("/family/stats")
    public FamilyStats getFamilyStats() {
    	long totalfamilies = userAnalyticsService.getTotalFamiles();
    	
		return FamilyStats.builder()
				.totalFamilies(totalfamilies)
				.build();
    }
    
    @GetMapping("/user/stats")
    public UserStats getUserStats() {
    	Map<String, Integer> roleDistribution = userAnalyticsService.getRoleDistribution();
    	long totalUsers = userAnalyticsService.getTotalUsers();
    	
        return UserStats.builder()
        		.roleDistribution(roleDistribution)
        		.totalUsers(totalUsers)
        		.build();
    }
    
}
