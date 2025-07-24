package com.adrasha.data.family.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.data.family.service.FamilyAnalyticsService;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController	
@RequestMapping("/data/families/analytics/")
public class FamilyAnalyticsController {

    private final FamilyAnalyticsService familyAnalyticsService;

    public FamilyAnalyticsController(FamilyAnalyticsService familyAnalyticsService) {
        this.familyAnalyticsService = familyAnalyticsService;
    }

    @GetMapping("/poverty")
    public Map<PovertyStatus, Long> getPovertyStatusCounts() {
        return familyAnalyticsService.getPovertyStatusCounts();
    }

    @GetMapping("/locality")
    public Map<LocalityType, Long> getLocalityCounts() {
        return familyAnalyticsService.getLocalityCounts();
    }
}
