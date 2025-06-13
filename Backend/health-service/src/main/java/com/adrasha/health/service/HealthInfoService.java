package com.adrasha.health.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.health.model.HealthInfo;

public interface HealthInfoService {

    Page<HealthInfo> getAllFamilies(Pageable pageable);
    
    HealthInfo getHealthInfo(UUID HealthInfoId) throws NotFoundException;
    
    HealthInfo createHealthInfo(HealthInfo HealthInfo) throws AlreadyExistsException;
    
    HealthInfo updateHealthInfo(UUID HealthInfoId, HealthInfo updatedHealthInfoDetails) throws NotFoundException;
    
    HealthInfo deleteHealthInfo(UUID HealthInfoId) throws NotFoundException;
}
