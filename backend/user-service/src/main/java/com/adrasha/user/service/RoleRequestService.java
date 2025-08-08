package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.filter.RoleRequestFilterDTO;
import com.adrasha.core.dto.response.RoleRequestResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.user.dto.roleRequest.RoleRequestCreateDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestUpdateDTO;

public interface RoleRequestService {

	Page<RoleRequestResponseDTO> getRoleRequestPage(RoleRequestFilterDTO filterDTO, Pageable pageable);
	
	Long getRoleRequestCount(RoleRequestFilterDTO filterDTO);
    
	RoleRequestResponseDTO getRoleRequest(UUID roleRequestId) throws NotFoundException;
	    
	RoleRequestResponseDTO createRoleRequest(RoleRequestCreateDTO createDTO) throws AlreadyExistsException;
    
	RoleRequestResponseDTO updateRoleRequest(UUID roleRequesId, RoleRequestUpdateDTO updateDTO) throws NotFoundException;
    
	RoleRequestResponseDTO deleteRoleRequest(UUID roleRequestId) throws NotFoundException;

	void approveRoleRequest(UUID requestId);

	void rejectRoleRequest(UUID requestId);
  
}
