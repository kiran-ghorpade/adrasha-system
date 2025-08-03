package com.adrasha.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.Member;

// Note : use For CRUD only
public interface MemberDataService {

    Page<Member> getMemberPage(Example<Member> example, Pageable pageable);
    
	List<Member> getMemberList(Example<Member> example);
	
	Long getMemberCount(Example<Member> example);
	
    // CRUD
    Member getMember(UUID MemberId) throws NotFoundException;
    
    Member createMember(Member Member) throws AlreadyExistsException;
    
    Member updateMember(UUID MemberId, Member updatedMemberDetails) throws NotFoundException;
    
    Member deleteMember(UUID MemberId) throws NotFoundException;
}
