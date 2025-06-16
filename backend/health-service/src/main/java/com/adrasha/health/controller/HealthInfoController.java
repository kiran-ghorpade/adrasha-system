package com.adrasha.health.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.adrasha.core.dto.Response;
import com.adrasha.health.dto.HealthDTO;
import com.adrasha.health.dto.HealthResponseDTO;
import com.adrasha.health.model.HealthInfo;
import com.adrasha.health.service.HealthInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/healthInfo")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
})
@Tag(name = "Health Management")
public class HealthInfoController {

	@Autowired
	private HealthInfoService healthService;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/{id}")
    @Operation(summary = "Get a health by ID", description = "Returns a health object based on the provided ID")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<HealthDTO>> getHealth(@Parameter(description = "ID of the health to retrieve", required = true) @PathVariable UUID id){
		
		HealthInfo request = healthService.getHealthInfo(id);
		HealthDTO dto = mapper.map(request, HealthDTO.class);
		
		Response<HealthDTO> apiResponse = Response.<HealthDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Health Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
    @Operation(summary = "Create new Health", description = "Returns created health")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<HealthResponseDTO>> createHealth(@Valid @RequestBody HealthDTO healthRegistrationDTO){
		
		HealthInfo healthInfo = mapper.map(healthRegistrationDTO, HealthInfo.class);
		
		HealthInfo newhealth = healthService.createHealthInfo(healthInfo);
		HealthResponseDTO dto = mapper.map(newhealth, HealthResponseDTO.class);
		
		Response<HealthResponseDTO> apiResponse = Response.<HealthResponseDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("Health created with member id "+ dto.getMemberId())
				.payload(dto)
				.build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getMemberId())
				.toUri();
		
		return ResponseEntity.created(location).body(apiResponse);
	}
	
	@PutMapping("/{id}")
    @Operation(summary = "Update a healthinfo by ID", description = "Returns a updated healthinfo based on the provided ID")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<HealthResponseDTO>> udpateHealth(
			@Parameter(description = "ID of the health to be updated", required = true)
			@PathVariable UUID id,
			@Valid @RequestBody HealthDTO updatedHealth
			){
		
		HealthInfo health = mapper.map(updatedHealth, HealthInfo.class);
		health = healthService.updateHealthInfo(id, health);
		HealthResponseDTO dto = mapper.map(health, HealthResponseDTO.class);
		
		Response<HealthResponseDTO> apiResponse = Response.<HealthResponseDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Updated Health Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ASHA')")
    @Operation(summary = "Delete Health", description = "This will delete all health data associated with member")
	public ResponseEntity<Void> deleteRoleRequestOs(@PathVariable UUID memberId){
		
		healthService.deleteHealthInfo(memberId);
		
		return ResponseEntity.noContent().build();
	}
	
}
