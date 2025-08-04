package com.adrasha.user.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.user.event.RoleRequestEventProducer;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.repository.RoleRequestRepository;
import com.adrasha.user.service.RoleRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleRequestServiceImpl implements RoleRequestService {

	private final RoleRequestRepository  requestRepository;
	private final ModelMapper modelMapper;
	private final RoleRequestEventProducer eventProducer;

	@Override
	public Page<RoleRequest> getRoleRequestPage(Example<RoleRequest> example, Pageable pageable) {

		return requestRepository.findAll(example, pageable);
	}
	
	@Override
	public Long getRoleRequestCount(Example<RoleRequest> example) {
		return requestRepository.count(example);
	}

	@Override
	public RoleRequest getRoleRequest(UUID roleRequestId) {

		return requestRepository.findById(roleRequestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
	}

	@Override
	public RoleRequest createRoleRequest(RoleRequest request) {
		
		List<RoleRequest> existingRequests = requestRepository.findByUserId(request.getUserId());
		
		Optional<RoleRequest> req = existingRequests.stream().filter(item-> item.getStatus().equals(request.getStatus())).findFirst();	
		
	  	if(req.isPresent()) {
	  		throw new AlreadyExistsException("error.roleRequest.alreadyExists");
	  	}

		RoleRequest createdRequest = requestRepository.save(request);
		
		eventProducer.sendCreatedEvent(createdRequest);
		
		return createdRequest;
	}

	@Override
	public RoleRequest updateRoleRequest(UUID requestId, RoleRequest updatedRoleRequest) {
		RoleRequest request = getRoleRequest(requestId);
		modelMapper.map(updatedRoleRequest, request);
		
		RoleRequest savedRequest = requestRepository.save(request);
		
		eventProducer.sendUpdatedEvent(updatedRoleRequest, savedRequest);
		
		return savedRequest;
	}

	@Override
	public RoleRequest deleteRoleRequest(UUID requestId) {
		RoleRequest request = getRoleRequest(requestId);
		requestRepository.delete(request);
		
		eventProducer.sendDeletedEvent(request);
		return request;
	}
}
