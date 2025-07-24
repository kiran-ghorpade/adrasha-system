package com.adrasha.analytics.client;

import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "data-service",contextId = "HealthAnalyticsClient" )
public interface HealthAnalyticsClient {
    @GetMapping("/data/health/analytics/pregnancy")
    Long getPregnantCount();

    @GetMapping("/data/health/analytics/ncd")
    Map<UUID, Long> getNcdCounts();
}
