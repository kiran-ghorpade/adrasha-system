package com.adrasha.user.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.dto.Response;
import com.adrasha.core.model.Role;
import com.adrasha.user.dto.AddRoleDTO;
import com.adrasha.user.dto.RoleRequestDTO;
import com.adrasha.user.dto.RoleRequestResponseDTO;
import com.adrasha.user.model.RequestStatus;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.service.AuthService;
import com.adrasha.user.service.RoleRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// TODO : Review and complete this controller

@RestController
@RequestMapping("/roleRequests")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
})
@Tag(name = "RoleRequest Management")
@PreAuthorize("hasRole('USER')")
public class RoleRequestController {
	
		@Autowired
		private RoleRequestService roleRequestService;
		
		@Autowired
		private AuthService authService;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
        @Operation(summary = "Get all roleRequest", description = "Returns a roleRequest in pageable")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<Page<RoleRequestResponseDTO>>> getAllRoleRequests(
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			Page<RoleRequest> rolePages = roleRequestService.getAllRoleRequest(pageable);
			Page<RoleRequestResponseDTO> RoleRequestResponseDTOPage = rolePages.map(roleRequest -> mapper.map(roleRequest, RoleRequestResponseDTO.class));
			
			Response<Page<RoleRequestResponseDTO>> apiResponse = Response.<Page<RoleRequestResponseDTO>>builder()
					.status(HttpStatus.OK.value())
					.message("List of Role Requests")
					.payload(RoleRequestResponseDTOPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
        @Operation(summary = "Get a roleRequest by ID", description = "Returns a roleRequest object based on the provided ID")
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
		
		// TODO : Decide whether add /me to every service or not
		@GetMapping("/me")
        @Operation(summary = "Get role requests of current logged in user", description = "Returns a role request of currently logged in user.")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> getCurrentUserRoleRequest(Authentication authentication) {
			
			if(authentication == null) {
				return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
			}
			
			JwtUser user = (JwtUser) authentication.getPrincipal();
			
			RoleRequest request = roleRequestService.getRoleRequest(user.getId());
			RoleRequestResponseDTO dto = mapper.map(request, RoleRequestResponseDTO.class);
			
			Response<RoleRequestResponseDTO> apiResponse = Response.<RoleRequestResponseDTO>builder()
					.status(HttpStatus.OK.value())
					.message("Role Request Details")
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping
        @Operation(summary = "Create new RoleRequest", description = "Returns created role request")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> createRoleRequest(@Valid @RequestBody RoleRequestDTO requestDTO){
			
			RoleRequest request = mapper.map(requestDTO, RoleRequest.class);
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
		@Operation(summary = "Update RoleRequest by ID", description = "Returns an updated role request")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<RoleRequestResponseDTO>> updateRoleRequest(@Parameter(description = "ID of the user to be updated", required = true) @PathVariable UUID id,@Valid @RequestBody RoleRequestDTO updatedRequest){
			
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
        @Operation(summary = "Delete RoleRequest", description = "deletes rolerequest and return nothing if successful deletion")
		public ResponseEntity<Void> deleteRoleRequestOs(@PathVariable UUID id){
			
			roleRequestService.deleteRoleRequest(id);
			
			return ResponseEntity.noContent().build();
		}
		
		@PostMapping("/approve/{id}")
		public ResponseEntity<Void> approveUserRequest(@PathVariable UUID id, Role requestedRole)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			authService.updateRole(AddRoleDTO.builder()
					.userId(id)
					.role(requestedRole)
					.build()
					);
			request.setStatus(RequestStatus.APPROVED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
		}
		
		@PostMapping("/reject/{id}")
		public ResponseEntity<Void> rejectUserRequest(@PathVariable UUID id, Role requestedRole)
		{
			RoleRequest request = roleRequestService.getRoleRequest(id);
			request.setStatus(RequestStatus.REJECTED);
			roleRequestService.updateRoleRequest(id, request);
			
			return ResponseEntity.ok().build();			
		}

}
