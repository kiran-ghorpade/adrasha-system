package com.adrasha.monolithic.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.monolithic.exception.VaccineNotFoundException;
import com.adrasha.monolithic.model.Vaccine;

public interface VaccineService {

	Page<Vaccine> getAllVaccines(Pageable pageable);
	
	Vaccine getVaccineById(UUID vaccineId) throws VaccineNotFoundException;
	
	Vaccine addVaccine(Vaccine vaccine);
	
	Vaccine updateVaccine(UUID vaccineId, Vaccine updatedVaccine) throws VaccineNotFoundException;
	
	void deleteVaccine(UUID vaccineId) throws VaccineNotFoundException;
}
