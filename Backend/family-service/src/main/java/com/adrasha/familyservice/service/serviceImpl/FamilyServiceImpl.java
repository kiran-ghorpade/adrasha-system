package com.adrasha.familyservice.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.familyservice.exception.FamilyNotFoundException;
import com.adrasha.familyservice.model.Family;
import com.adrasha.familyservice.model.Member;
import com.adrasha.familyservice.repository.FamilyRepository;
import com.adrasha.familyservice.service.FamilyService;

@Service
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	private FamilyRepository familyRepository;

	@Override
	public Page<Family> getAllFamilies(Pageable pageable) {

		return familyRepository.findAll(pageable);
	}

	@Override
	public Family getFamily(UUID familyId) {

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

	public Family getFamilyByHead(Member member) {
		return familyRepository.findByHeadMember(member)
				.orElseThrow(() -> new FamilyNotFoundException("Family Not Found with Head Member : " + member));
	}

}
