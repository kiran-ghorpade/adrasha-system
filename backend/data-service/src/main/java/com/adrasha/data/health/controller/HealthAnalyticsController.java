package com.adrasha.data.health.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.data.health.service.HealthAnalyticsService;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/data/health/analytics")
public class HealthAnalyticsController {

    private final HealthAnalyticsService healthAnalyticsService;

    public HealthAnalyticsController(HealthAnalyticsService healthAnalyticsService) {
        this.healthAnalyticsService = healthAnalyticsService;
    }

    @GetMapping("/pregnancy")
    public Long getPregnantCount() {
        return healthAnalyticsService.getPregnantCount();
    }

    @GetMapping("/ncd")
    public Map<UUID, Long> getNcdCounts() {
        return healthAnalyticsService.getNcdCounts();
    }
}
