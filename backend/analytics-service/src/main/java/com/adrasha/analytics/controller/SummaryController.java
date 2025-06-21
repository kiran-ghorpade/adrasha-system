package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.dto.FamilyStats;
import com.adrasha.analytics.dto.MasterdataStats;
import com.adrasha.analytics.dto.MemberStats;
import com.adrasha.analytics.service.FamilyAnalyticsService;
import com.adrasha.analytics.service.MasterDataAnalyticsService;
import com.adrasha.analytics.service.MemberAnalyticsService;
import com.adrasha.analytics.service.UserAnalyticsService;
import com.adrasha.analytics.user.dto.UserStats;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.core.model.Role;

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
	
	@Autowired
	private MemberAnalyticsService memberAnalyticsService;
	
	@Autowired
	private FamilyAnalyticsService familyAnalyticsService;
	
	@Autowired
	private MasterDataAnalyticsService masterDataAnalyticsService;

    @GetMapping("/family/stats")
    public FamilyStats getFamilyStats() {
    	long totalMembers = familyAnalyticsService.getTotalFamiliesCount();
    	Map<PovertyStatus, Long> povertyStats = familyAnalyticsService.getPovertyStats();
    	
		return FamilyStats.builder()
				.totalFamilies(totalMembers)
				.povertyStats(povertyStats)
				.build();
    }
    
    @GetMapping("/member/stats")
    public MemberStats getMemberStats() {
    	long totalMembers = memberAnalyticsService.getTotalMembers();
    	double avgMembers = memberAnalyticsService.getAverageMembersPerFamily();
    	Map<Gender, Long> genderDistribution = memberAnalyticsService.getGenderDistribution();
        Map<AgeGroup, Long>  ageDistribution =  memberAnalyticsService.getAgeDistribution();
    	
		return MemberStats.builder()
				.totalMembers(totalMembers)
				.averageMembersPerFamily(avgMembers)
				.genderDistribution(genderDistribution)
				.ageDistribution(ageDistribution)
				.build();
    }
    
    @GetMapping("/user/stats")
    public UserStats getUserStats() {
    	Map<Role, Long> roleDistribution = userAnalyticsService.getRoleDistribution();
    	long totalUsers = userAnalyticsService.getTotalUsers();
    	
        return UserStats.builder()
        		.totalUsers(totalUsers)
        		.roleDistribution(roleDistribution)
        		.build();
    }
    
    @GetMapping("/masterdata/stats")
    public MasterdataStats getMasterDataStats() {
    	long totalHealthCenters = masterDataAnalyticsService.getHealthCenterCount();
    	long totalLocations = masterDataAnalyticsService.getLocationCount();
    	long totalNCD = masterDataAnalyticsService.getNCDCount();
    	
        return MasterdataStats.builder()
        		.totalHealthCenters(totalHealthCenters)
        		.totalLocations(totalLocations)
        		.totalNCD(totalNCD)
        		.build();
    }
}
