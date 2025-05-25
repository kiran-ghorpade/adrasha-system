package com.adrasha.ncd.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.ncd.exception.VaccineNotFoundException;
import com.adrasha.ncd.model.Vaccine;

public interface VaccineService {

	Page<Vaccine> getAllVaccines(Pageable pageable);
	
	Vaccine getVaccine(UUID vaccineId) throws VaccineNotFoundException;
	
	Vaccine createVaccine(Vaccine vaccine);
	
	Vaccine updateVaccine(UUID vaccineId, Vaccine updatedVaccine) throws VaccineNotFoundException;
	
	void deleteVaccine(UUID vaccineId) throws VaccineNotFoundException;
}
