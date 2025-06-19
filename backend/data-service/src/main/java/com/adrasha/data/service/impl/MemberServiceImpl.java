package com.adrasha.data.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.Member;
import com.adrasha.data.repository.FamilyRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.MemberDataService;

@Service
public class MemberServiceImpl implements MemberDataService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private FamilyRepository familyRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Page<Member> getAllMembers(Example<Member> example, Pageable pageable) {

		return memberRepository.findAll(example, pageable);
	}

	@Override
	public Member getMember(UUID memberId) {

		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("Member Not Found with id : " + memberId));
	}

	@Override
	public Member createMember(Member member) {
		
		if(!familyRepository.existsById(member.getFamilyId())) {
			throw new NotFoundException("Family Not Found with id : "+ member.getFamilyId());
		}
		
		if(memberRepository.existsByAdharNumber(member.getAdharNumber())) {
			throw new AlreadyExistsException("Member with adharId : "+ member.getAdharNumber()+" already present");
		}
		
		return memberRepository.save(member);
	}

	@Override
	public Member updateMember(UUID memberId, Member updatedMemberDetails) {
		Member member = getMember(memberId);
		modelMapper.map(updatedMemberDetails, member);
		return memberRepository.save(member);
	}

	@Override
	public Member deleteMember(UUID memberId) {
		Member member = getMember(memberId);
		memberRepository.delete(member);
		return member;
	}

}
