package com.adrasha.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.filter.FamilyFilterDTO;
import com.adrasha.core.dto.reports.FamilyReportDTO;
import com.adrasha.core.dto.response.FamilyResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.family.dto.FamilyRegistrationDTO;
import com.adrasha.data.family.dto.FamilyUpdateDTO;

public interface FamilyDataService {

	Page<FamilyResponseDTO> getFamilyPage(FamilyFilterDTO filterDTO, Pageable pageable);
	
	List<FamilyReportDTO> getFamilyList(FamilyFilterDTO filterDTO);
	
	Long getFamilyCount(FamilyFilterDTO example);
		
	// CRUD	
	FamilyResponseDTO getFamily(UUID familyId) throws NotFoundException;

	FamilyResponseDTO createFamily(FamilyRegistrationDTO createDTO) throws AlreadyExistsException;

	FamilyResponseDTO updateFamily(UUID familyId, FamilyUpdateDTO updateDTO) throws NotFoundException;

	FamilyResponseDTO deleteFamily(UUID familyId) throws NotFoundException;
}