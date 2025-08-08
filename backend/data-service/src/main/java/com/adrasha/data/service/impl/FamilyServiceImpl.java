package com.adrasha.data.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrasha.core.dto.filter.FamilyFilterDTO;
import com.adrasha.core.dto.reports.FamilyReportDTO;
import com.adrasha.core.dto.response.FamilyResponseDTO;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.utils.ExampleMatcherUtils;
import com.adrasha.data.event.FamilyEventProducer;
import com.adrasha.data.family.dto.FamilyRegistrationDTO;
import com.adrasha.data.family.dto.FamilyUpdateDTO;
import com.adrasha.data.integration.UserDataService;
import com.adrasha.data.model.Family;
import com.adrasha.data.model.Member;
import com.adrasha.data.repository.FamilyRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.FamilyDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyDataService {

	private final FamilyRepository familyRepository;
	private final MemberRepository memberRepository;
	private final UserDataService userDataService;
	private final FamilyEventProducer eventProducer;
	private final ModelMapper mapper;

	@Override
	public Page<FamilyResponseDTO> getFamilyPage(FamilyFilterDTO filterDTO, Pageable pageable) {

		Family searchTerms = mapper.map(filterDTO, Family.class);
		
		Example<Family> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		Page<Family> page = familyRepository.findAll(example, pageable);
		
		return page.map(family-> mapper.map(family, FamilyResponseDTO.class));
	}
	
	@Override
	public List<FamilyReportDTO> getFamilyList(FamilyFilterDTO filterDTO){
		
		Family searchTerms = mapper.map(filterDTO, Family.class);
		
		Example<Family> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		List<Family> list = familyRepository.findAll(example);
		
		return list.stream().map(family-> mapper.map(family, FamilyReportDTO.class)).toList();
	}
	
	@Override
	public Long getFamilyCount(FamilyFilterDTO filterDTO){
		
		Family searchTerms = mapper.map(filterDTO, Family.class);

		Example<Family> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		return familyRepository.count(example);
	}

	@Override
	public FamilyResponseDTO getFamily(UUID familyId) {
		return familyRepository.findById(familyId)
				.map(family-> mapper.map(family, FamilyResponseDTO.class))
				.orElseThrow(() -> new NotFoundException("error.family.notFound"));
	}

	@Override
	@Transactional
	public FamilyResponseDTO createFamily(FamilyRegistrationDTO registrationDTO) {

		// check asha exist
		userDataService.verifyUserExist(registrationDTO.getFamily().getAshaId());
		
		// map to family
		Family family = mapper.map(registrationDTO.getFamily(), Family.class);
		
		// save member
		Member headMember = mapper.map(registrationDTO.getHeadMember(), Member.class);
		
		headMember.setAshaId(family.getAshaId());
		headMember.setFamilyId(family.getAshaId()); // temp value
	
		Member newMember = memberRepository.save(headMember);
		
		// save family
		family.setHeadMemberId(newMember.getId());
		Family newFamily = familyRepository.save(family);
		
		// update member		
		newMember.setFamilyId(newFamily.getId());
		
		memberRepository.save(newMember);
		
		eventProducer.sendCreatedEvent(newFamily);

		return mapper.map(newFamily, FamilyResponseDTO.class);
	}

	@Override
	public FamilyResponseDTO updateFamily(UUID familyId, FamilyUpdateDTO updatedFamilyDetails) {

		Family oldFamily = familyRepository.findById(familyId)
				.orElseThrow(()-> new NotFoundException("error.family.notFound"));
		
		if(!memberRepository.existsById(updatedFamilyDetails.getHeadMemberId())){
			throw new NotFoundException("error.headMember.notFound");
		}
		
		Family updatedFamily = mapper.map(updatedFamilyDetails, Family.class);
		
		mapper.map(updatedFamily, oldFamily);
		
		userDataService.verifyUserExist(updatedFamilyDetails.getAshaId());
		
		Family savedFamily = familyRepository.save(oldFamily);
		
		eventProducer.sendUpdatedEvent(oldFamily, savedFamily);
		
		return mapper.map(savedFamily, FamilyResponseDTO.class);
	}

	@Override
	public FamilyResponseDTO deleteFamily(UUID familyId) {
		Family family = familyRepository.findById(familyId)
				.orElseThrow(()-> new NotFoundException("error.family.notFound"));
		
		familyRepository.delete(family);
		memberRepository.deleteByFamilyId(familyId);
		
		eventProducer.sendDeletedEvent(family);
		
		return mapper.map(family, FamilyResponseDTO.class);
	}

}
