package com.adrasha.user.service.serviceImpl;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.dto.filter.RoleRequestFilterDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;
import com.adrasha.core.utils.ExampleMatcherUtils;
import com.adrasha.user.dto.roleRequest.RoleRequestCreateDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestUpdateDTO;
import com.adrasha.user.event.RoleRequestEventProducer;
import com.adrasha.user.integration.AuthService;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.model.User;
import com.adrasha.user.repository.RoleRequestRepository;
import com.adrasha.user.repository.UserRepository;
import com.adrasha.user.service.RoleRequestService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleRequestServiceImpl implements RoleRequestService {

    private final UserRepository userRepository;

	private final RoleRequestRepository  requestRepository;

	private final AuthService authService;
	private final ModelMapper mapper;
	private final RoleRequestEventProducer eventProducer;


	@Override
	public Page<RoleRequestDTO> getRoleRequestPage(RoleRequestFilterDTO filterDTO, Pageable pageable) {

		RoleRequest searchTerms = mapper.map(filterDTO, RoleRequest.class);
		
		Example<RoleRequest> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		Page<RoleRequest> page =  requestRepository.findAll(example, pageable);
		
		return page.map(request-> mapper.map(request, RoleRequestDTO.class));
	}
	
	@Override
	public Long getRoleRequestCount(RoleRequestFilterDTO filterDTO) {

		RoleRequest searchTerms = mapper.map(filterDTO, RoleRequest.class);
		
		Example<RoleRequest> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		return requestRepository.count(example);
	}

	@Override
	public RoleRequestDTO getRoleRequest(UUID roleRequestId) {

		return requestRepository.findById(roleRequestId)
				.map(request-> mapper.map(request, RoleRequestDTO.class))
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
	}

	@Override
	public RoleRequestDTO createRoleRequest(RoleRequestCreateDTO createDTO) {
				
	  	if(requestRepository.existsByUserIdAndStatus(createDTO.getUserId(), RequestStatus.PENDING)) {
	  		throw new AlreadyExistsException("error.roleRequest.alreadyExists");
	  	}

	  	RoleRequest request = mapper.map(createDTO, RoleRequest.class);
	  	
		RoleRequest createdRequest = requestRepository.save(request);
		
		eventProducer.sendCreatedEvent(createdRequest);
		
		return mapper.map(createdRequest, RoleRequestDTO.class);
	}

	@Override
	public RoleRequestDTO updateRoleRequest(UUID requestId, RoleRequestUpdateDTO updatedRoleRequest) {
		RoleRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
		
		RoleRequest oldRequest = request;
		
		mapper.map(updatedRoleRequest, request);
		
		RoleRequest savedRequest = requestRepository.save(request);
		
		eventProducer.sendUpdatedEvent(oldRequest, savedRequest);
		
		return mapper.map(savedRequest, RoleRequestDTO.class);
	}

	@Override
	public RoleRequestDTO deleteRoleRequest(UUID requestId) {
		RoleRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
		
		requestRepository.delete(request);
		
		eventProducer.sendDeletedEvent(request);
		return mapper.map(request, RoleRequestDTO.class);
	}

	@Override
	@Transactional
	public void approveRoleRequest(UUID requestId) {
	    RoleRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));

	    // Auth service call
	    JwtUser user = authService.addRole(request);

	    Set<Role> roles = user.getRoles().stream()
	        .map(Role::valueOf)
	        .collect(Collectors.toSet());

	    User newUser = mapper.map(request, User.class);
	    newUser.setRoles(roles);
	    userRepository.save(newUser);

	    request.setStatus(RequestStatus.APPROVED);
	    requestRepository.save(request);
	}

	@Override
	public void rejectRoleRequest(UUID requestId) {
		RoleRequest request = requestRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("error.roleRequest.notFound"));
		
		request.setStatus(RequestStatus.REJECTED);
		requestRepository.save(request);
	}

}
