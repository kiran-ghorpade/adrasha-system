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
import com.adrasha.masterdata.dto.DistrictDTO;
import com.adrasha.masterdata.model.District;
import com.adrasha.masterdata.service.DistrictService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/districts")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "District Management")
public class DistrictController {

	@Autowired
	private DistrictService districtService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Page<District>>> getAllDistricts(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable,
			@RequestParam(required = false)
			UUID stateId
			){

		Page<District> page = districtService.getAllDistricts(pageable, stateId);

		Response<Page<District>> apiResponse = Response.<Page<District>>builder()
				.status(HttpStatus.OK.value())
				.message("List of Districts")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<DistrictDTO>> getdistrict(@Parameter(description = "ID of the district to retrieve", required = true) @PathVariable UUID id){

		District district = districtService.getDistrict(id);
		DistrictDTO dto = mapper.map(district, DistrictDTO.class);

		Response<DistrictDTO> apiResponse = Response.<DistrictDTO>builder()
				.status(HttpStatus.OK.value())
				.message("District Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<DistrictDTO>> createDistrict(@Valid @RequestBody DistrictDTO newDistrict){

		District district = mapper.map(newDistrict, District.class);
		district = districtService.createDistrict(district);
		DistrictDTO dto = mapper.map(district, DistrictDTO.class);

		Response<DistrictDTO> apiResponse = Response.<DistrictDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("District created with id "+ district.getId())
				.payload(dto)
				.build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(district.getId())
				.toUri();

		return ResponseEntity.created(location).body(apiResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<DistrictDTO>> udpateDistrict(
			@Parameter(description = "ID of the district to retrieve", required = true) 
			@PathVariable UUID id,
			@Valid 
			@RequestBody 
			DistrictDTO updatedDistrict){

		District district = mapper.map(updatedDistrict, District.class);
		district = districtService.updateDistrict(id, district);
		DistrictDTO dto = mapper.map(district, DistrictDTO.class);

		Response<DistrictDTO> apiResponse = Response.<DistrictDTO>builder()
				.status(HttpStatus.OK.value())
				.message("District Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDistrict(@PathVariable UUID id){

		districtService.deleteDistrict(id);

		return ResponseEntity.noContent().build();
	}

}
