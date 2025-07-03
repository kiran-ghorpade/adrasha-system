package com.adrasha.analytics.dto;

import java.util.Map;

import com.adrasha.core.model.PovertyStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class FamilyStats {
    private long totalFamilies;
    private Map<PovertyStatus, Long> povertyStats;
}