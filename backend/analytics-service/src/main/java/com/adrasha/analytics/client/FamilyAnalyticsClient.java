package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;

@FeignClient(name = "data-service", contextId = "FamilyAnalyticsClient")
public interface FamilyAnalyticsClient {
    @GetMapping("/data/family/analytics/poverty")
    Map<PovertyStatus, Long> getPovertyCounts();

    @GetMapping("/data/family/analytics/locality")
    Map<LocalityType, Long> getLocalityCounts();
}
