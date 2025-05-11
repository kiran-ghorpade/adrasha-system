package com.adrasha.healthprofile.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.healthprofile.model.Vaccination;

public interface VaccinationService {

	List<Vaccination> getVaccinationsByMemberId(UUID memberId);
	
	Page<Vaccination> getAllVaccinations(Pageable pageable);
	
	Vaccination getVaccinationById(UUID id);

	Vaccination createVaccination(Vaccination newVaccination);

	Vaccination updateVaccination(UUID id, Vaccination updatedVaccination);

	void deleteVaccination(UUID id);
}
