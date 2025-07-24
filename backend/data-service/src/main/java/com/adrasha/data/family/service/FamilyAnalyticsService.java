package com.adrasha.data.family.service;

import java.util.Map;

import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;

public interface FamilyAnalyticsService {
    Map<PovertyStatus, Long> getPovertyStatusCounts();
    Map<LocalityType, Long> getLocalityCounts();
}
