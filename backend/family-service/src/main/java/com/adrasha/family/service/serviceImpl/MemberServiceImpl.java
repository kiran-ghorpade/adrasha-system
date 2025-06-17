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
import com.adrasha.family.model.Member;
import com.adrasha.family.repository.MemberRepository;
import com.adrasha.family.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

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
	Optional<Member> exsistingMember = memberRepository.findByAdharNumber(member.getAdharNumber());
		
		if(exsistingMember.isPresent()) {
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
