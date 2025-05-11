package com.adrasha.familyservice.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.familyservice.model.Family;
import com.adrasha.familyservice.model.Member;
import com.adrasha.familyservice.repository.FamilyRepository;
import com.adrasha.familyservice.repository.MemberRepository;
import com.adrasha.familyservice.service.FamilyRegistrationService;


@Service
public class FamilyRegistrationServiceImpl implements FamilyRegistrationService {

	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Family registerFamilyWithHead(Member member, Family family) {
		
		memberRepository.save(member);
		familyRepository.save(family);
		return null;
	}

}
