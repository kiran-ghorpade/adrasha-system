package com.adrasha.data.family.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.data.family.repository.FamilyRepository;
import com.adrasha.data.family.service.FamilyAnalyticsService;

@Service
public class FamilyAnalyticsServiceImpl implements FamilyAnalyticsService {

    private final FamilyRepository familyRepository;

    public FamilyAnalyticsServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public Map<PovertyStatus, Long> getPovertyStatusCounts() {
        // Directly use repository analytics method
        return familyRepository.countByPovertyStatus();
    }

    @Override
    public Map<LocalityType, Long> getLocalityCounts() {
        return familyRepository.countByLocality();
    }
}
