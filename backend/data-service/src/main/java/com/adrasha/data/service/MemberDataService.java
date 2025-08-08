package com.adrasha.data.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.core.dto.response.MemberResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.member.dto.MemberCreateDTO;
import com.adrasha.data.member.dto.MemberUpdateDTO;

// Note : use For CRUD only
public interface MemberDataService {

	Page<MemberResponseDTO> getMemberPage(MemberFilterDTO filterDTO, Pageable pageable);

	List<MemberResponseDTO> getMemberList(MemberFilterDTO filterDTO);

	Long getMemberCount(MemberFilterDTO filterDTO);
	
	// CRUD
	MemberResponseDTO getMember(UUID MemberId) throws NotFoundException;

	MemberResponseDTO createMember(MemberCreateDTO createDTO) throws AlreadyExistsException;

	MemberResponseDTO updateMember(UUID MemberId, MemberUpdateDTO updateDTO) throws NotFoundException;

	MemberResponseDTO deleteMember(UUID MemberId) throws NotFoundException;
}
