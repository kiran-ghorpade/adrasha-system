package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.Subdistrict;

public interface SubdistrictService {
	
	// subdistrict

	Page<Subdistrict> getAllSubdistricts(Pageable pageable, UUID districtId);
	
	Subdistrict getSubdistrict(UUID id) throws NotFoundException;
	
	Subdistrict createSubdistrict(Subdistrict subdistrict) throws AlreadyExistsException;
	
	Subdistrict updateSubdistrict(UUID id, Subdistrict subdistrict) throws NotFoundException;
	
	void deleteSubdistrict(UUID id) throws NotFoundException;
	
}
