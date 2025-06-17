package com.adrasha.family.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.family.model.Family;
import com.adrasha.family.model.Member;
import com.adrasha.family.repository.FamilyRepository;
import com.adrasha.family.service.FamilyService;

@Service
public class FamilyServiceImpl implements FamilyService {

	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Family> getAllFamilies(Example<Family> example, Pageable pageable) {

		return familyRepository.findAll(example,pageable);
	}

	@Override
	public Family getFamily(UUID familyId) {

		return familyRepository.findById(familyId)
				.orElseThrow(() -> new NotFoundException("Family Not Found with id : " + familyId));
	}

	@Override
	public Family createFamily(Family family) {
		
		Optional<Family> exsistingFamily = familyRepository.findByHeadMember(family.getHeadMember());
		
		if(exsistingFamily.isPresent()) {
			throw new AlreadyExistsException("Family with headId : "+ family.getHeadMember().getId()+" already present");
		}
		
		return familyRepository.save(family);
	}

	@Override
	public Family updateFamily(UUID familyId, Family updatedFamilyDetails) {
		Family family = getFamily(familyId);
		modelMapper.map(updatedFamilyDetails, family);
		return familyRepository.save(family);
	}

	@Override
	public Family deleteFamily(UUID familyId) {
		Family family = getFamily(familyId);
		familyRepository.delete(family);
		return family;
	}

	public Family getFamilyByHead(Member member) {
		return familyRepository.findByHeadMember(member)
				.orElseThrow(() -> new NotFoundException("Family Not Found with Head Member : " + member));
	}

}
