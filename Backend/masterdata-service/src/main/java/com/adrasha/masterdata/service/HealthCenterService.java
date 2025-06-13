package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.HealthCenter;

public interface HealthCenterService {

	// country
	Page<HealthCenter> getAllHealthCenters(Pageable pageable);
	
	HealthCenter getHealthCenter(UUID id) throws NotFoundException;
	
	HealthCenter createHealthCenter(HealthCenter country) throws AlreadyExistsException;
	
	HealthCenter updateHealthCenter(UUID id, HealthCenter country) throws NotFoundException;
	
	void deleteHealthCenter(UUID id) throws NotFoundException;
	
}
