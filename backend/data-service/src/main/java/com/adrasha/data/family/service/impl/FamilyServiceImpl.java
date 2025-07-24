package com.adrasha.data.family.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.family.model.Family;
import com.adrasha.data.family.repository.FamilyRepository;
import com.adrasha.data.family.service.FamilyDataService;
import com.adrasha.data.integration.RemoteDataService;
import com.adrasha.data.member.model.Member;
import com.adrasha.data.member.repository.MemberRepository;

@Service
public class FamilyServiceImpl implements FamilyDataService {

	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private RemoteDataService remoteDataService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Family> getAllFamilies(Example<Family> example, Pageable pageable) {

		return familyRepository.findAll(example, pageable);
	}

	@Override
	public long getCount(Example<Family> example) {
		return familyRepository.count(example);
	}
	
	@Override
	public Family getFamily(UUID familyId) {

		return familyRepository.findById(familyId)
				.orElseThrow(() -> new NotFoundException("error.family.notFound"));
	}

	@Override
	@Transactional
	public Family createFamily(Family family, Member headMember) {
		
		remoteDataService.verifyUserExist(family.getAshaId());

		headMember.setAshaId(family.getAshaId());
		headMember.setFamilyId(family.getAshaId()); // temp value
		Member newMember = memberRepository.save(headMember);
		
		family.setHeadMemberId(newMember.getId());
		Family newFamily = familyRepository.save(family);
		
		newMember.setFamilyId(newFamily.getId());
		memberRepository.save(newMember);

		return newFamily;
	}

	@Override
	public Family updateFamily(UUID familyId, Family updatedFamilyDetails) {

		Family family = getFamily(familyId);
		modelMapper.map(updatedFamilyDetails, family);
		
		if(!memberRepository.existsById(family.getHeadMemberId())){
			throw new NotFoundException("error.headMember.notFound");
		}
		
		remoteDataService.verifyUserExist(updatedFamilyDetails.getAshaId());
		
		return familyRepository.save(family);
	}

	@Override
	public Family deleteFamily(UUID familyId) {
		Family family = getFamily(familyId);
		familyRepository.delete(family);
		return family;
	}



}
