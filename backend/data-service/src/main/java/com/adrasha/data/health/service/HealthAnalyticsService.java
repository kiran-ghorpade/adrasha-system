package com.adrasha.data.health.service;

import java.util.Map;
import java.util.UUID;

public interface HealthAnalyticsService {
    Long getPregnantCount();
    Map<UUID, Long> getNcdCounts();
}
