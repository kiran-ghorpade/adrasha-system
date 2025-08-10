package com.adrasha.user.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.dto.filter.RoleRequestFilterDTO;
import com.adrasha.core.dto.page.RoleRequestPageResponseDTO;
import com.adrasha.core.dto.response.RoleRequestResponseDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestCreateDTO;
import com.adrasha.user.dto.roleRequest.RoleRequestUpdateDTO;
import com.adrasha.user.service.RoleRequestService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roleRequests")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "RoleRequest")
@ApiResponses({
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
@PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
public class RoleRequestController {

	private final RoleRequestService roleRequestService;

	@GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RoleRequestPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@PreAuthorize("hasAnyRole('ADMIN','USER','SYSTEM')")
	public Page<RoleRequestResponseDTO> getAllRoleRequests(RoleRequestFilterDTO filterDTO,
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return roleRequestService.getRoleRequestPage(filterDTO, pageable);

	}

	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	private Long getCount(RoleRequestFilterDTO filterDTO) {
		return roleRequestService.getRoleRequestCount(filterDTO);
	}

	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RoleRequestResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasAnyRole('ADMIN','USER','SYSTEM')")
	public RoleRequestResponseDTO getRoleRequest(@PathVariable UUID id) {

		return roleRequestService.getRoleRequest(id);

	}

	@PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RoleRequestResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<RoleRequestResponseDTO> createRoleRequest(
			@Valid @RequestBody RoleRequestCreateDTO requestDTO) {

		RoleRequestResponseDTO dto = roleRequestService.createRoleRequest(requestDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(location).body(dto);
	}

	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RoleRequestResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("#id.toString() == authentication.principal.id")
	public RoleRequestResponseDTO updateRoleRequest(
			@Parameter(description = "ID of the user to be updated", required = true) @PathVariable UUID id,
			@Valid @RequestBody RoleRequestUpdateDTO updatedRequest) {

		return roleRequestService.updateRoleRequest(id, updatedRequest);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("#id.toString() == authentication.principal.id or hasAnyRole('ADMIN')")
	public ResponseEntity<Void> deleteRoleRequest(@PathVariable UUID id) {

		roleRequestService.deleteRoleRequest(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/approve/{id}")
	@ApiResponse(responseCode = "200", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> approveUserRequest(@PathVariable UUID id) {
		roleRequestService.approveRoleRequest(id);
		return ResponseEntity.ok().build();

	}

	@PutMapping("/reject/{id}")
	@ApiResponse(responseCode = "200", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> rejectUserRequest(@PathVariable UUID id) {
		roleRequestService.rejectRoleRequest(id);
		return ResponseEntity.ok().build();
	}

}
