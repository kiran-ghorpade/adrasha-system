package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.user.exception.RoleRequestAlreadyExistsException;
import com.adrasha.user.exception.RoleRequestNotFoundException;
import com.adrasha.user.model.RoleRequest;

public interface RoleRequestService {

	Page<RoleRequest> getAllRoleRequest(Pageable pageable);
    
	RoleRequest getRoleRequest(UUID roleRequestId) throws RoleRequestNotFoundException;
	
	RoleRequest getRoleRequestByUserId(UUID userId) throws RoleRequestNotFoundException;
    
	RoleRequest createRoleRequest(RoleRequest roleRequest) throws RoleRequestAlreadyExistsException;
    
	RoleRequest updateRoleRequest(UUID roleRequesId, RoleRequest updatedRoleRequest) throws RoleRequestNotFoundException;
    
	RoleRequest deleteRoleRequest(UUID roleRequestId) throws RoleRequestNotFoundException;
  
}
