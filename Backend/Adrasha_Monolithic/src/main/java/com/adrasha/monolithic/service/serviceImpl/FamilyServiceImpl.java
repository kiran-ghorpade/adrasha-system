package com.adrasha.monolithic.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.monolithic.exception.FamilyNotFoundException;
import com.adrasha.monolithic.model.Family;
import com.adrasha.monolithic.repository.FamilyRepository;
import com.adrasha.monolithic.service.FamilyService;

public class FamilyServiceImpl implements FamilyService {

	@Autowired
	private FamilyRepository familyRepository;

	@Override
	public Page<Family> getAllFamilies(Pageable pageable) {

		return familyRepository.findAll(pageable);
	}

	@Override
	public Family getFamily(UUID familyId) throws FamilyNotFoundException {

		return familyRepository.findById(familyId)
				.orElseThrow(() -> new FamilyNotFoundException("Family Not Found with id : " + familyId));
	}

	@Override
	public Family createFamily(Family family) {
		return familyRepository.save(family);
	}

	@Override
	public Family updateFamily(UUID familyId, Family updatedFamilyDetails) {

		return familyRepository.save(updatedFamilyDetails);
	}

	@Override
	public Family deleteFamily(UUID familyId) {

		return null;
	}

	public Family getFamilyByHead(UUID memberId) throws FamilyNotFoundException {
		return familyRepository.findByHeadMember(memberId)
				.orElseThrow(() -> new FamilyNotFoundException("Family Not Found with Head Member : " + memberId));
	}

}
