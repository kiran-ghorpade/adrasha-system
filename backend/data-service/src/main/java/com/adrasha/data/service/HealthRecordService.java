package com.adrasha.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.HealthRecord;

public interface HealthRecordService {

    Page<HealthRecord> getHealthRecordPage(Example<HealthRecord> example, Pageable pageable);
        
	List<HealthRecord> getHealthRecordList(Example<HealthRecord> example);
	
	Long getHealthRecordCount(Example<HealthRecord> example);
	
	// CRUD
	
    HealthRecord getHealthRecord(UUID HealthRecordId) throws NotFoundException;
    
    HealthRecord createHealthRecord(HealthRecord HealthRecord) throws AlreadyExistsException;
    
    HealthRecord updateHealthRecord(UUID HealthRecordId, HealthRecord updatedHealthRecordDetails) throws NotFoundException;
    
    HealthRecord deleteHealthRecord(UUID HealthRecordId) throws NotFoundException;
}
