package com.adrasha.analytics.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.service.AnalyticsAggregatorService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsAggregatorService aggregatorService;

    public AnalyticsController(AnalyticsAggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        return aggregatorService.getFullAnalyticsOverview();
    }
}
