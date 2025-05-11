package com.adrasha.vaccination.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.vaccination.exception.VaccineNotFoundException;
import com.adrasha.vaccination.model.Vaccine;
import com.adrasha.vaccination.repository.VaccineRepository;
import com.adrasha.vaccination.service.VaccineService;

public class VaccineServiceImpl implements VaccineService {

	@Autowired
	private VaccineRepository repository;

	@Override
	public Page<Vaccine> getAllVaccines(Pageable pageable) {

		return repository.findAll(pageable);
	}

	@Override
	public Vaccine getVaccine(UUID VaccineId) throws VaccineNotFoundException {

		return repository.findById(VaccineId)
				.orElseThrow(() -> new VaccineNotFoundException("Vaccine Not Found with id : " + VaccineId));
	}

	@Override
	public Vaccine createVaccine(Vaccine Vaccine) {
		return repository.save(Vaccine);
	}

	@Override
	public Vaccine updateVaccine(UUID VaccineId, Vaccine updatedVaccineDetails) {

		return repository.save(updatedVaccineDetails);
	}

	@Override
	public void deleteVaccine(UUID VaccineId) {
		repository.deleteById(VaccineId);
	}
}
