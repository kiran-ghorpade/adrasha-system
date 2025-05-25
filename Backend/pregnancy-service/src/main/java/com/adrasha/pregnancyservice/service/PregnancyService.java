package com.adrasha.pregnancyservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.pregnancyservice.model.Pregnancy;

import jakarta.validation.Valid;


public interface PregnancyService {
	
	Pregnancy createPregnancy(Pregnancy dto);

	Pregnancy getPregnancy(UUID id);

	List<Pregnancy> getAllPregnanciesForMember(UUID motherId);

	Page<Pregnancy> getAllPregnancies(Pageable pageable);

	Pregnancy updatePregnancy(UUID id, @Valid Pregnancy updatedPregnancy);

	void deletePregnancy(UUID id);
}
