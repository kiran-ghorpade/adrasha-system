package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.Locality;

public interface LocalityService {
	// locality
	Page<Locality> getAllLocalities(Pageable pageable, UUID subdistrictId);
	
	Locality getLocality(UUID id) throws NotFoundException;
	
	Locality createLocality(Locality locality) throws AlreadyExistsException;
	
	Locality updateLocality(UUID id, Locality locality) throws NotFoundException;
	
	void deleteLocality(UUID id) throws NotFoundException;
}
