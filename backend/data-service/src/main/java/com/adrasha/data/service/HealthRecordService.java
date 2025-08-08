package com.adrasha.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.filter.HealthRecordFilterDTO;
import com.adrasha.core.dto.reports.HealthRecordReportDTO;
import com.adrasha.core.dto.response.HealthRecordResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.health.records.dto.HealthRecordCreateDTO;
import com.adrasha.data.health.records.dto.HealthRecordUpdateDTO;

public interface HealthRecordService {

    Page<HealthRecordResponseDTO> getHealthRecordPage(HealthRecordFilterDTO filterDTO, Pageable pageable);
        
	List<HealthRecordReportDTO> getHealthRecordList(HealthRecordFilterDTO filterDTO);
	
	Long getHealthRecordCount(HealthRecordFilterDTO filterDTO);
	
	// CRUD
	
    HealthRecordResponseDTO getHealthRecord(UUID HealthRecordId) throws NotFoundException;
    
    HealthRecordResponseDTO createHealthRecord(HealthRecordCreateDTO createDTO) throws AlreadyExistsException;
    
    HealthRecordResponseDTO updateHealthRecord(UUID HealthRecordId, HealthRecordUpdateDTO updateDTO) throws NotFoundException;
    
    HealthRecordResponseDTO deleteHealthRecord(UUID HealthRecordId) throws NotFoundException;
}
