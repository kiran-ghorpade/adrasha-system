package com.adrasha.user.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.user.exception.RoleRequestNotFoundException;
import com.adrasha.user.exception.UserAlreadyExistsException;
import com.adrasha.user.exception.UserNotFoundException;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.repository.RoleRequestRepository;
import com.adrasha.user.service.RoleRequestService;

// TODO : Review class 
@Service
public class RoleRequestServiceImpl implements RoleRequestService {

	@Autowired
	private RoleRequestRepository  requestRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<RoleRequest> getAllRoleRequest(Pageable pageable) {

		return requestRepository.findAll(pageable);
	}

	@Override
	public RoleRequest getRoleRequest(UUID roleRequestId) {

		return requestRepository.findById(roleRequestId)
				.orElseThrow(() -> new UserNotFoundException("RoleRequest Not Found with id : " + roleRequestId));
	}

	@Override
	public RoleRequest createRoleRequest(RoleRequest request) {
		
				Optional<RoleRequest> existingRequest = requestRepository.findByUserId(request.getUserId());
		
	  	if(existingRequest.isPresent()) {
	  		throw new UserAlreadyExistsException("RoleRequest with user id : "+ request.getUserId()+" already exist.");
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
	public RoleRequest getRoleRequestByUserId(UUID userId) throws RoleRequestNotFoundException {

		return requestRepository.findByUserId(userId)
				.orElseThrow(()-> new RoleRequestNotFoundException("Role request with userid "+ userId +"not found"));
	}

}
