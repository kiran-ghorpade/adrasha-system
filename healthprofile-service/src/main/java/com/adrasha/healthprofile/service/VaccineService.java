package com.adrasha.healthprofile.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.healthprofile.exception.VaccineNotFoundException;
import com.adrasha.healthprofile.model.Vaccine;

public interface VaccineService {

	Page<Vaccine> getAllVaccines(Pageable pageable);
	
	Vaccine getVaccine(UUID vaccineId) throws VaccineNotFoundException;
	
	Vaccine addVaccine(Vaccine vaccine);
	
	Vaccine updateVaccine(UUID vaccineId, Vaccine updatedVaccine) throws VaccineNotFoundException;
	
	void deleteVaccine(UUID vaccineId) throws VaccineNotFoundException;
}
