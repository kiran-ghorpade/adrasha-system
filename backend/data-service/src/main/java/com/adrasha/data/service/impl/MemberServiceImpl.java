package com.adrasha.data.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.event.MemberEventProducer;
import com.adrasha.data.model.Member;
import com.adrasha.data.repository.FamilyRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.MemberDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberDataService {

	private final MemberRepository memberRepository;
	private final FamilyRepository familyRepository;
	private final MemberEventProducer eventProducer;
	private final ModelMapper modelMapper;
	
	@Override
	public Page<Member> getMemberPage(Example<Member> example, Pageable pageable) {
		return memberRepository.findAll(example, pageable);
	}
	
	@Override
	public List<Member> getMemberList(Example<Member> example){
		return memberRepository.findAll(example);
	}
	
	@Override
	public Long getMemberCount(Example<Member> example){
		return memberRepository.count(example);
	}

	@Override
	public Member getMember(UUID memberId) {

		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("error.member.notFound"));
	}

	@Override
	public Member createMember(Member member) {
		
		if(!familyRepository.existsById(member.getFamilyId())) {
			throw new NotFoundException("error.family.notFound");
		}
		
		if(memberRepository.existsByAdharNumber(member.getAdharNumber())) {
			throw new AlreadyExistsException("error.member.alreadyExists");
		}
		
		Member createdMember = memberRepository.save(member);

		eventProducer.sendCreatedEvent(createdMember);
		
		return createdMember;
	}

	@Override
	public Member updateMember(UUID memberId, Member updatedMemberDetails) {
		Member member = getMember(memberId);
	    
		modelMapper.map(updatedMemberDetails, member);
		Member updatedMember = memberRepository.save(member);
				
		eventProducer.sendUpdatedEvent(updatedMemberDetails, updatedMember);	        
		
		return updatedMember;	
	}

	@Override
	public Member deleteMember(UUID memberId) {
		Member member = getMember(memberId);
		memberRepository.delete(member);
		
		eventProducer.sendDeletedEvent(member);

		return member;
	}

}
