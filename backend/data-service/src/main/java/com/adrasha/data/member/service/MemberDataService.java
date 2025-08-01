package com.adrasha.data.member.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.member.model.Member;

public interface MemberDataService {

    Page<Member> getAllMembers(Example<Member> example, Pageable pageable);
    
    long getCount(Example<Member> example);
        
    Member getMember(UUID MemberId) throws NotFoundException;
    
    Member createMember(Member Member) throws AlreadyExistsException;
    
    Member updateMember(UUID MemberId, Member updatedMemberDetails) throws NotFoundException;
    
    Member deleteMember(UUID MemberId) throws NotFoundException;
}
