package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.District;

public interface DistrictService {

	// district
	Page<District> getAllDistricts(Pageable pageable, UUID stateId);
	
	District getDistrict(UUID id) throws NotFoundException;
	
	District createDistrict(District district) throws AlreadyExistsException;
	
	District updateDistrict(UUID id, District district) throws NotFoundException;
	
	void deleteDistrict(UUID id) throws NotFoundException;
}
