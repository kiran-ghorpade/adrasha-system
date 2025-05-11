package com.adrasha.healthprofile.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.healthprofile.model.Pregnancy;

import jakarta.validation.Valid;


public interface PregnancyService {
	
	Pregnancy createPregnancy(Pregnancy dto);

	Pregnancy getPregnancyById(UUID id);

	List<Pregnancy> getAllPregnanciesForMember(UUID motherId);

	Page<Pregnancy> getAllPregnancies(Pageable pageable);

	Pregnancy updatePregnancy(UUID id, @Valid Pregnancy updatedPregnancy);

	void deletePregnancy(UUID id);
}
