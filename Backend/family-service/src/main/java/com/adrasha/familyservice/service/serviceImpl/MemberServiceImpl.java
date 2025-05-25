package com.adrasha.familyservice.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.familyservice.exception.MemberNotFoundException;
import com.adrasha.familyservice.model.Member;
import com.adrasha.familyservice.repository.MemberRepository;
import com.adrasha.familyservice.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository repository;

	@Override
	public Page<Member> getAllMembers(Pageable pageable) {

		return repository.findAll(pageable);
	}

	@Override
	public Member getMember(UUID memberId) {

		return repository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException("Member Not Found with id : " + memberId));
	}

	@Override
	public Member createMember(Member member) {
		return repository.save(member);
	}

	@Override
	public Member updateMember(UUID memberId, Member updatedMemberDetails) {

		return repository.save(updatedMemberDetails);
	}

	@Override
	public Member deleteMember(UUID memberId) {

		return null;
	}

}
