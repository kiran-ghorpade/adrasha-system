package com.adrasha.data.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.core.dto.reports.FamilyReportDTO;
import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.dto.response.MemberResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.utils.ExampleMatcherUtils;
import com.adrasha.data.event.MemberEventProducer;
import com.adrasha.data.member.dto.MemberCreateDTO;
import com.adrasha.data.member.dto.MemberUpdateDTO;
import com.adrasha.data.model.Member;
import com.adrasha.data.repository.FamilyRepository;
import com.adrasha.data.repository.HealthRecordRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.MemberDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberDataService {

	private final MemberRepository memberRepository;
	private final FamilyRepository familyRepository;
	private final HealthRecordRepository healthRecordRepository;
	private final MemberEventProducer eventProducer;
	private final ModelMapper mapper;

	@Override
	public Page<MemberResponseDTO> getMemberPage(MemberFilterDTO filterDTO, Pageable pageable) {

		Member searchTerms = mapper.map(filterDTO, Member.class);
		Example<Member> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Page<Member> page = memberRepository.findAll(example, pageable);

		return page.map(member -> mapper.map(member, MemberResponseDTO.class));
	}

	@Override
	public List<MemberReportDTO> getMemberList(MemberFilterDTO filterDTO) {

		Member searchTerms = mapper.map(filterDTO, Member.class);
		Example<Member> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		List<Member> list = memberRepository.findAll(example);

		return list.stream()
				.map(member ->{
					MemberReportDTO out = mapper.map(member, MemberReportDTO.class);
					out.setName(member.getName().getFullName());
					return out;
				})
				.toList();
	}

	@Override
	public Long getMemberCount(MemberFilterDTO filterDTO) {

		Member searchTerms = mapper.map(filterDTO, Member.class);
		Example<Member> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		return memberRepository.count(example);
	}

	@Override
	public MemberResponseDTO getMember(UUID memberId) {

		return memberRepository.findById(memberId).map(member -> mapper.map(member, MemberResponseDTO.class))
				.orElseThrow(() -> new NotFoundException("error.member.notFound"));
	}

	@Override
	public MemberResponseDTO createMember(MemberCreateDTO createDTO) {

		if (!familyRepository.existsById(createDTO.getFamilyId())) {
			throw new NotFoundException("error.family.notFound");
		}

		if (memberRepository.existsByAdharNumber(createDTO.getAdharNumber())) {
			throw new AlreadyExistsException("error.member.alreadyExists");
		}

		Member member = mapper.map(createDTO, Member.class);

		Member createdMember = memberRepository.save(member);

		eventProducer.sendCreatedEvent(createdMember);

		return mapper.map(createdMember, MemberResponseDTO.class);
	}

	@Override
	public MemberResponseDTO updateMember(UUID memberId, MemberUpdateDTO updateDTO) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("error.member.notFound"));

		if (!familyRepository.existsById(updateDTO.getFamilyId())) {
			throw new NotFoundException("error.family.notFound");
		}

		Member oldMember = mapper.map(member, Member.class);

		mapper.map(updateDTO, member);

		Member savedMember = memberRepository.save(member);

		eventProducer.sendUpdatedEvent(oldMember, savedMember);

		return mapper.map(savedMember, MemberResponseDTO.class);
	}

	@Override
	public MemberResponseDTO deleteMember(UUID memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("error.member.notFound"));

		if (!familyRepository.existsById(member.getFamilyId())) {
			throw new NotFoundException("error.family.notFound");
		}

//		Long count = this.getMemberCount(MemberFilterDTO.builder().familyId(member.getFamilyId()).build());
//		
//		if(count == 1){ 
//			familyRepository.deleteById(member.getFamilyId());
//		}

		memberRepository.delete(member);

		healthRecordRepository.deleteByMemberId(memberId);

		eventProducer.sendDeletedEvent(member);

		return mapper.map(member, MemberResponseDTO.class);
	}

}
