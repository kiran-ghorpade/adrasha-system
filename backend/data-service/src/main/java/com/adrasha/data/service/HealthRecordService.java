package com.adrasha.data.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.HealthRecord;

public interface HealthRecordService {

    Page<HealthRecord> getAllRecords(Example<HealthRecord> example, Pageable pageable);
    
    HealthRecord getHealthRecord(UUID HealthRecordId) throws NotFoundException;
    
    HealthRecord createHealthRecord(HealthRecord HealthRecord) throws AlreadyExistsException;
    
    HealthRecord updateHealthRecord(UUID HealthRecordId, HealthRecord updatedHealthRecordDetails) throws NotFoundException;
    
    HealthRecord deleteHealthRecord(UUID HealthRecordId) throws NotFoundException;
}
