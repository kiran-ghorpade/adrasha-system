package com.adrasha.healthprofile.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.healthprofile.exception.MemberNotFoundException;
import com.adrasha.healthprofile.model.HealthProfile;
import com.adrasha.healthprofile.model.Member;

import jakarta.validation.Valid;

public interface HealthProfileService {

    Page<Member> getAllVaccination(Pageable pageable);
    
    
    Member getMember(UUID MemberId) throws MemberNotFoundException;
    
    Member createMember(Member Member) throws MemberNotFoundException;
    
    Member updateMember(UUID MemberId, Member updatedMemberDetails) throws MemberNotFoundException;
    
    Member deleteMember(UUID MemberId) throws MemberNotFoundException;

	Member createHealthProfile(@Valid HealthProfile healthProfile);
}
