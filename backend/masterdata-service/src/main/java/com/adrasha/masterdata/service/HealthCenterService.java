package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.HealthCenter;

public interface HealthCenterService{
		
	Page<HealthCenter> getAll(Example<HealthCenter> example, Pageable pageable);
	
	long getCount(Example<HealthCenter> example);
	
	HealthCenter get(UUID id) throws NotFoundException;
	
	HealthCenter create(HealthCenter entity) throws AlreadyExistsException;
	
	HealthCenter update(UUID id, HealthCenter entity) throws NotFoundException;
	
	void delete(UUID id) throws NotFoundException;



}
