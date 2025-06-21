package com.adrasha.analytics.dto;

import java.util.Map;

import com.adrasha.core.model.PovertyStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyStats {
    private long totalFamilies;
    private Map<PovertyStatus, Long> povertyStats;
}