package com.adrasha.familyservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.familyservice.exception.MemberNotFoundException;
import com.adrasha.familyservice.model.Member;

public interface MemberService {

    Page<Member> getAllMembers(Pageable pageable);
        
    Member getMember(UUID MemberId) throws MemberNotFoundException;
    
    Member createMember(Member Member) throws MemberNotFoundException;
    
    Member updateMember(UUID MemberId, Member updatedMemberDetails) throws MemberNotFoundException;
    
    Member deleteMember(UUID MemberId) throws MemberNotFoundException;
}
