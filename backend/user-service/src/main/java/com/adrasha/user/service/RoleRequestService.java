package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.user.model.RoleRequest;

public interface RoleRequestService {

	Page<RoleRequest> getAllRoleRequest(Example<RoleRequest> example, Pageable pageable);
    
	RoleRequest getRoleRequest(UUID roleRequestId) throws NotFoundException;
	
	RoleRequest getRoleRequestByUserId(UUID userId) throws NotFoundException;
    
	RoleRequest createRoleRequest(RoleRequest roleRequest) throws AlreadyExistsException;
    
	RoleRequest updateRoleRequest(UUID roleRequesId, RoleRequest updatedRoleRequest) throws NotFoundException;
    
	RoleRequest deleteRoleRequest(UUID roleRequestId) throws NotFoundException;

	long getTotalRequestCount(Example<RoleRequest> example);
  
}
