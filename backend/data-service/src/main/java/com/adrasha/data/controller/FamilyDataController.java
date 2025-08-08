package com.adrasha.data.controller;

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
import com.adrasha.core.dto.filter.FamilyFilterDTO;
import com.adrasha.core.dto.page.FamilyPageResponseDTO;
import com.adrasha.core.dto.response.FamilyResponseDTO;
import com.adrasha.data.family.dto.FamilyRegistrationDTO;
import com.adrasha.data.family.dto.FamilyUpdateDTO;
import com.adrasha.data.service.FamilyDataService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/families")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "FamilyData")
@ApiResponses({
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
@PreAuthorize("hasAnyRole('ASHA', 'SYSTEM')")
public class FamilyDataController {

	// Dependencies
	private final FamilyDataService familyService;

	// Get All Families
	@GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Page<FamilyResponseDTO> getFamilyPage(FamilyFilterDTO filterDTO,
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

		return familyService.getFamilyPage(filterDTO, pageable);
	}

	// Get All Count
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Long getFamilyCount(FamilyFilterDTO filterDTO) {

		Long count = familyService.getFamilyCount(filterDTO);

		return count;
	}

	// Get a FamilyDTO
	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public FamilyResponseDTO getFamily(@PathVariable UUID id) {

		return familyService.getFamily(id);
	}

	// Create new FamilyDTO
	@PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = FamilyResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<FamilyResponseDTO> createFamily(
			@Valid @RequestBody FamilyRegistrationDTO familyRegistrationDTO) {

		FamilyResponseDTO dto = familyService.createFamily(familyRegistrationDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(location).body(dto);
	}

	// Update a FamilyDTO
	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public FamilyResponseDTO updateFamily(@PathVariable UUID id, @Valid @RequestBody FamilyUpdateDTO updatedFamily) {

		return familyService.updateFamily(id, updatedFamily);

	}

	// Delete a family
	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> deleteFamily(@PathVariable UUID id) {

		familyService.deleteFamily(id);

		return ResponseEntity.noContent().build();
	}

}
