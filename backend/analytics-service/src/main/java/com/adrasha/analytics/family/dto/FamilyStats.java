package com.adrasha.analytics.family.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyStats {
    private long totalFamilies;
    private double averageMembersPerFamily;
    private PovertyStatus povertyStats;
   	private RegistrationStatus registrationStats;
}