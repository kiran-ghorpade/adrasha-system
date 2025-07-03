package com.adrasha.user.service.serviceImpl;

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
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.repository.RoleRequestRepository;
import com.adrasha.user.service.RoleRequestService;

@Service
public class RoleRequestServiceImpl implements RoleRequestService {

	@Autowired
	private RoleRequestRepository  requestRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<RoleRequest> getAllRoleRequest(Example<RoleRequest> example, Pageable pageable) {

		return requestRepository.findAll(example, pageable);
	}

	@Override
	public RoleRequest getRoleRequest(UUID roleRequestId) {

		return requestRepository.findById(roleRequestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
	}

	@Override
	public RoleRequest createRoleRequest(RoleRequest request) {
		
		Optional<RoleRequest> existingRequest = requestRepository.findByUserId(request.getUserId());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("error.roleRequest.alreadyExists");
	  	}

		return requestRepository.save(request);
	}

	@Override
	public RoleRequest updateRoleRequest(UUID requestId, RoleRequest updatedRoleRequest) {
		RoleRequest request = getRoleRequest(requestId);
		modelMapper.map(updatedRoleRequest, request);
		return requestRepository.save(request);
	}

	@Override
	public RoleRequest deleteRoleRequest(UUID requestId) {
		RoleRequest request = getRoleRequest(requestId);
		requestRepository.delete(request);
		return request;
	}

	@Override
	public RoleRequest getRoleRequestByUserId(UUID userId) throws NotFoundException {

		return requestRepository.findByUserId(userId)
				.orElseThrow(()-> new NotFoundException("error.roleRequest.notFound"));
	}

	@Override
	public long getTotalRequestCount(Example<RoleRequest> example) {
		return requestRepository.count(example);
	}

}
