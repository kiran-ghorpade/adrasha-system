package com.adrasha.user.controller;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.exception.UnAuthorizedException;
import com.adrasha.core.filter.dto.RoleRequestFilterDTO;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;
import com.adrasha.core.response.dto.RoleRequestResponseDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestCreateDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestUpdateDTO;
import com.adrasha.user.integration.AuthService;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.model.User;
import com.adrasha.user.service.RoleRequestService;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/roleRequests")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "RoleRequest Management")
@PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
public class RoleRequestController {
	
		@Autowired
		private RoleRequestService roleRequestService;
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private AuthService authService;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
		public Page<RoleRequestResponseDTO> getAllRoleRequests(
				RoleRequestFilterDTO filterDTO,
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			RoleRequest searchTerms = mapper.map(filterDTO, RoleRequest.class);
			
			Example<RoleRequest> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
			
			Page<RoleRequest> rolePages = roleRequestService.getAllRoleRequest(example, pageable);
			
			Page<RoleRequestResponseDTO> roleRequestResponseDTOPage = rolePages.map(roleRequest -> mapper.map(roleRequest, RoleRequestResponseDTO.class));
		
			return roleRequestResponseDTOPage;
		}
		
		@GetMapping("/count")
		public Map<String, Long> getTotalRoleRequests(RoleRequestFilterDTO filterDTO) {
			RoleRequest filter = mapper.map(filterDTO, RoleRequest.class);

			Example<RoleRequest> example = Example.of(filter, ExampleMatcherUtils.getDefaultMatcher());

			long total = roleRequestService.getTotalRequestCount(example);
			return Map.of("count", total);
		}

		
		@GetMapping("/{id}")
		public RoleRequestResponseDTO getRoleRequest(@PathVariable UUID id){
			
			RoleRequest request = roleRequestService.getRoleRequest(id);
			return mapper.map(request, RoleRequestResponseDTO.class);

		}
		
		@GetMapping("/me")
		@PreAuthorize("hasAnyRole('USER', 'SYSTEM')")
		public RoleRequestResponseDTO getCurrentUserRoleRequest(Authentication authentication) {

			if(authentication == null) {
				throw new UnAuthorizedException();
			}
			
			// if principal change, then please change this code.
			UUID userId = UUID.fromString(authentication.getPrincipal().toString());
			
			RoleRequest request = roleRequestService.getRoleRequestByUserId(userId);
			
			return mapper.map(request, RoleRequestResponseDTO.class);
			
		}
		
		@PostMapping
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<RoleRequestResponseDTO> createRoleRequest(@Valid @RequestBody RoleRequestCreateDTO requestDTO){
			
			RoleRequest request = mapper.map(requestDTO, RoleRequest.class);
			request.setId(null);
			request.setUserId(requestDTO.getUserId());
			request.setStatus(RequestStatus.PENDING);
			
			request = roleRequestService.createRoleRequest(request);
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
			
			return ResponseEntity.created(location).body(dto);
		}
		
		@PutMapping("/{id}")
		@PreAuthorize("#id.toString() == authentication.principal.toString()")
		public RoleRequestResponseDTO updateRoleRequest(
				@Parameter(description = "ID of the user to be updated", required = true) 
				@PathVariable 
				UUID id,
				@Valid @RequestBody RoleRequestUpdateDTO updatedRequest){
			
			RoleRequest request = mapper.map(updatedRequest, RoleRequest.class);
			request = roleRequestService.updateRoleRequest(id, request);
			return mapper.map(request, RoleRequestResponseDTO.class);
			
		}
		
		@DeleteMapping("/{id}")
		@PreAuthorize("#id.toString() == authentication.principal.toString() or hasAnyRole('ADMIN')")
		public ResponseEntity<Void> deleteRoleRequest(@PathVariable UUID id){
			
			roleRequestService.deleteRoleRequest(id);
			
			return ResponseEntity.noContent().build();
		}
		
		@PutMapping("/approve/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> approveUserRequest(@PathVariable UUID id)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			
			// auth service call
			JwtUser user = authService.addRole(request);
			
			Set<Role> roles =  user.getRoles().stream()
									.map(Role::valueOf)
									.collect(Collectors.toSet());
			
			User newUser = mapper.map(request, User.class);
			newUser.setRoles(roles);
			userService.createUser(newUser);
			
			request.setStatus(RequestStatus.APPROVED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
			
		}
		
		@PutMapping("/reject/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> rejectUserRequest(@PathVariable UUID id)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			request.setStatus(RequestStatus.REJECTED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
		}

}
