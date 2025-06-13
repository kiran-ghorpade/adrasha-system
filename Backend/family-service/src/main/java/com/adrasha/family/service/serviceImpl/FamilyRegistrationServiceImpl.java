package com.adrasha.family.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrasha.family.model.Family;
import com.adrasha.family.model.Member;
import com.adrasha.family.service.FamilyRegistrationService;
import com.adrasha.family.service.FamilyService;
import com.adrasha.family.service.MemberService;


@Service
public class FamilyRegistrationServiceImpl implements FamilyRegistrationService {

	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private MemberService memberService;

	@Override
	@Transactional
	public Family registerFamilyWithHead(Family family, Member headMember) {
		
		Member newMember = memberService.createMember(headMember);
		family.setHeadMember(newMember);
		return familyService.createFamily(family);
	}

}
