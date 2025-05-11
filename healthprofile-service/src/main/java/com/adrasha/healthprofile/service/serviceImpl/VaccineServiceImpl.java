package com.adrasha.healthprofile.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.healthprofile.exception.VaccineNotFoundException;
import com.adrasha.healthprofile.model.Vaccine;
import com.adrasha.healthprofile.repository.VaccineRepository;
import com.adrasha.healthprofile.service.VaccineService;

public class VaccineServiceImpl implements VaccineService {

	@Autowired
	private VaccineRepository vaccineRepository;

	@Override
	public Page<Vaccine> getAllVaccines(Pageable pageable) {

		return vaccineRepository.findAll(pageable);
	}

	@Override
	public Vaccine getVaccine(UUID VaccineId) throws VaccineNotFoundException {

		return vaccineRepository.findById(VaccineId)
				.orElseThrow(() -> new VaccineNotFoundException("Vaccine Not Found with id : " + VaccineId));
	}

	@Override
	public Vaccine addVaccine(Vaccine Vaccine) {
		return vaccineRepository.save(Vaccine);
	}

	@Override
	public Vaccine updateVaccine(UUID VaccineId, Vaccine updatedVaccineDetails) {

		return vaccineRepository.save(updatedVaccineDetails);
	}

	@Override
	public void deleteVaccine(UUID VaccineId) {

		vaccineRepository.deleteById(VaccineId);
	}

}
