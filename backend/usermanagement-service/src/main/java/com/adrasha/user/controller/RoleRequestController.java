package com.adrasha.user.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.adrasha.core.dto.Response;
import com.adrasha.core.exception.UnAuthorizedException;
import com.adrasha.user.dto.roleRequest.RoleRequestCreateDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestFilterDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestResponseDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestUpdateDTO;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;
import com.adrasha.user.model.RequestStatus;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.model.User;
import com.adrasha.user.service.AuthService;
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
@PreAuthorize("hasRole('USER')")
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
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<Page<RoleRequestResponseDTO>>> getAllRoleRequests(
				
				RoleRequestFilterDTO filterDTO,
				
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			RoleRequest searchTerms = mapper.map(filterDTO, RoleRequest.class);
			
			Example<RoleRequest> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
			
			Page<RoleRequest> rolePages = roleRequestService.getAllRoleRequest(example, pageable);
			
			Page<RoleRequestResponseDTO> RoleRequestResponseDTOPage = rolePages.map(roleRequest -> mapper.map(roleRequest, RoleRequestResponseDTO.class));
			
			Response<Page<RoleRequestResponseDTO>> apiResponse = Response.<Page<RoleRequestResponseDTO>>builder()
					.status(HttpStatus.OK.value())
					.message("List of Role Requests")
					.payload(RoleRequestResponseDTOPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> getRoleRequest(@Parameter(description = "ID of the roleRequest to retrieve", required = true) @PathVariable UUID id){
			
			RoleRequest request = roleRequestService.getRoleRequest(id);
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);
			
			Response<RoleRequestResponseDTO> apiResponse = Response.<RoleRequestResponseDTO>builder()
					.status(HttpStatus.OK.value())
					.message("RoleRequest Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/me")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> getCurrentUserRoleRequest(Authentication authentication) {

			if(authentication == null) {
				throw new UnAuthorizedException();
			}
			
			// if principal change, then please change this code.
			UUID userId = UUID.fromString(authentication.getPrincipal().toString());
			
			RoleRequest request = roleRequestService.getRoleRequestByUserId(userId);
			
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);
			
			Response<RoleRequestResponseDTO> apiResponse = Response.<RoleRequestResponseDTO>builder()
					.status(HttpStatus.OK.value())
					.message("Role Request Details")
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> createRoleRequest(@Valid @RequestBody RoleRequestCreateDTO requestDTO){
			
			RoleRequest request = mapper.map(requestDTO, RoleRequest.class);
			request.setId(null);
			request.setUserId(requestDTO.getUserId());
			request.setStatus(RequestStatus.PENDING);
			
			request = roleRequestService.createRoleRequest(request);
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);
			
			Response<RoleRequestResponseDTO> apiResponse = Response.<RoleRequestResponseDTO>builder()
					.status(HttpStatus.CREATED.value())
					.message("RoleRequest created with id "+ dto.getId())
					.payload(dto)
					.build();
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
			
			return ResponseEntity.created(location).body(apiResponse);
		}
		
		@PutMapping("/{id}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> updateRoleRequest(
				@Parameter(description = "ID of the user to be updated", required = true) 
				@PathVariable 
				UUID id,
				@Valid @RequestBody RoleRequestUpdateDTO updatedRequest){
			
			RoleRequest request = mapper.map(updatedRequest, RoleRequest.class);
			request = roleRequestService.updateRoleRequest(id, request);
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);
			
			Response<RoleRequestResponseDTO> apiResponse = Response.<RoleRequestResponseDTO>builder()
					.status(HttpStatus.OK.value())
					.message("Role Request Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@DeleteMapping("/{id}")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Void> deleteRoleRequest(@PathVariable UUID id){
			
			roleRequestService.deleteRoleRequest(id);
			
			return ResponseEntity.noContent().build();
		}
		
		@PostMapping("/approve/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> approveUserRequest(@PathVariable UUID id)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			
			HttpStatusCode status = authService.updateRole(RoleUpdateDTO.builder()
					.userId(id)
					.role(request.getRole())
					.build()
					).getStatusCode();	

			// return if status is not 200
			if(!status.equals(HttpStatus.OK)) {
				return ResponseEntity.internalServerError().build();
			}
				
			User newUser = mapper.map(request, User.class);
			userService.createUser(newUser);
			
			request.setStatus(RequestStatus.APPROVED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
			
		}
		
		@PostMapping("/reject/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> rejectUserRequest(@PathVariable UUID id)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			request.setStatus(RequestStatus.REJECTED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
		}

}
