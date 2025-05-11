package com.adrasha.pregnancyservice.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.pregnancyservice.exception.PregnancyNotFoundException;
import com.adrasha.pregnancyservice.model.Pregnancy;
import com.adrasha.pregnancyservice.repository.PregnancyRepository;
import com.adrasha.pregnancyservice.service.PregnancyService;

public class PregnancyServiceImpl implements PregnancyService {

	@Autowired
	private PregnancyRepository pregnancyRepository;

	@Override
	public Page<Pregnancy> getAllPregnancies(Pageable pageable) {

		return pregnancyRepository.findAll(pageable);
	}

	@Override
	public Pregnancy getPregnancy(UUID PregnancyId) throws PregnancyNotFoundException {

		return pregnancyRepository.findById(PregnancyId)
				.orElseThrow(() -> new PregnancyNotFoundException("Pregnancy Not Found with id : " + PregnancyId));
	}

	@Override
	public Pregnancy createPregnancy(Pregnancy Pregnancy) {
		return pregnancyRepository.save(Pregnancy);
	}

	@Override
	public Pregnancy updatePregnancy(UUID PregnancyId, Pregnancy updatedPregnancyDetails) {

		return pregnancyRepository.save(updatedPregnancyDetails);
	}

	@Override
	public void deletePregnancy(UUID PregnancyId) {

		pregnancyRepository.deleteById(PregnancyId);
	}

	@Override
	public List<Pregnancy> getAllPregnanciesForMember(UUID motherId) {
		return pregnancyRepository.findByMotherId(motherId);
	}
}
