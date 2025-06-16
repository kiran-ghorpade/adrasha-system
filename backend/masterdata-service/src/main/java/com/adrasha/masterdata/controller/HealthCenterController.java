package com.adrasha.masterdata.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.dto.HealthCenterDTO;
import com.adrasha.masterdata.model.HealthCenter;
import com.adrasha.masterdata.model.HealthCenterType;
import com.adrasha.masterdata.model.Locality;
import com.adrasha.masterdata.service.HealthCenterService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/healthCenters")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Health Center Management")
public class HealthCenterController {

	@Autowired
	private HealthCenterService healthCenterService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Page<HealthCenter>>> getAllHealthCenters(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable,
			@RequestParam(required = false)
			HealthCenterType type,
			@RequestParam(required = false)
			Locality locality
			){

		Page<HealthCenter> page = healthCenterService.getAllHealthCenters(pageable);

		Response<Page<HealthCenter>> apiResponse = Response.<Page<HealthCenter>>builder()
				.status(HttpStatus.OK.value())
				.message("List of HealthCenters")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<HealthCenterDTO>> gethealthCenter(@Parameter(description = "ID of the healthCenter to retrieve", required = true) @PathVariable UUID id){

		HealthCenter healthCenter = healthCenterService.getHealthCenter(id);
		HealthCenterDTO dto = mapper.map(healthCenter, HealthCenterDTO.class);

		Response<HealthCenterDTO> apiResponse = Response.<HealthCenterDTO>builder()
				.status(HttpStatus.OK.value())
				.message("HealthCenter Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<HealthCenterDTO>> createHealthCenter(@Valid @RequestBody HealthCenterDTO newHealthCenter){

		HealthCenter healthCenter = mapper.map(newHealthCenter, HealthCenter.class);
		healthCenter = healthCenterService.createHealthCenter(healthCenter);
		HealthCenterDTO dto = mapper.map(healthCenter, HealthCenterDTO.class);

		Response<HealthCenterDTO> apiResponse = Response.<HealthCenterDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("HealthCenter created with id "+ dto.getId())
				.payload(dto)
				.build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();

		return ResponseEntity.created(location).body(apiResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<HealthCenterDTO>> udpateHealthCenter(@PathVariable UUID id, @Valid @RequestBody HealthCenterDTO updatedHealthCenter){

		HealthCenter healthCenter = mapper.map(updatedHealthCenter, HealthCenter.class);
		healthCenter = healthCenterService.updateHealthCenter(id, healthCenter);
		HealthCenterDTO dto = mapper.map(healthCenter, HealthCenterDTO.class);

		Response<HealthCenterDTO> apiResponse = Response.<HealthCenterDTO>builder()
				.status(HttpStatus.OK.value())
				.message("HealthCenter Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHealthCenter(@PathVariable UUID id){

		healthCenterService.deleteHealthCenter(id);

		return ResponseEntity.noContent().build();
	}

}
