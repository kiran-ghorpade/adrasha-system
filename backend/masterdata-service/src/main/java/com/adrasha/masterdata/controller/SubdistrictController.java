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
import com.adrasha.masterdata.dto.SubdistrictDTO;
import com.adrasha.masterdata.model.District;
import com.adrasha.masterdata.model.Subdistrict;
import com.adrasha.masterdata.service.DistrictService;
import com.adrasha.masterdata.service.SubdistrictService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/subdistricts")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "SubDistrict Management")
public class SubdistrictController {

	@Autowired
	private SubdistrictService subdistrictService;
	
	@Autowired
	private DistrictService districtService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Page<Subdistrict>>> getAllSubdistricts(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable,
			@RequestParam(required = false)
			UUID districId
			){

		Page<Subdistrict> page = subdistrictService.getAllSubdistricts(pageable, districId);

		Response<Page<Subdistrict>> apiResponse = Response.<Page<Subdistrict>>builder()
				.status(HttpStatus.OK.value())
				.message("List of Subdistricts")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<SubdistrictDTO>> getsubdistrict(@Parameter(description = "ID of the subdistrict to retrieve", required = true) @PathVariable UUID id){

		Subdistrict subdistrict = subdistrictService.getSubdistrict(id);
		SubdistrictDTO dto = mapper.map(subdistrict, SubdistrictDTO.class);

		Response<SubdistrictDTO> apiResponse = Response.<SubdistrictDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Subdistrict Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<SubdistrictDTO>> createSubdistrict(@Valid @RequestBody SubdistrictDTO newSubdistrict){

		District district = districtService.getDistrict(newSubdistrict.getDistrict());

		Subdistrict subdistrict = mapper.map(newSubdistrict, Subdistrict.class);
		subdistrict.setDistrict(district);
		
		subdistrict = subdistrictService.createSubdistrict(subdistrict);
		SubdistrictDTO dto = mapper.map(subdistrict, SubdistrictDTO.class);

		Response<SubdistrictDTO> apiResponse = Response.<SubdistrictDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("Subdistrict created with id "+ dto.getId())
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
	public ResponseEntity<Response<SubdistrictDTO>> udpateSubdistrict(@PathVariable UUID id, @Valid @RequestBody SubdistrictDTO updatedSubdistrict){

		District district = districtService.getDistrict(updatedSubdistrict.getDistrict());

		Subdistrict subdistrict = mapper.map(updatedSubdistrict, Subdistrict.class);
		subdistrict.setDistrict(district);
		
		subdistrict = subdistrictService.updateSubdistrict(id, subdistrict);
		SubdistrictDTO dto = mapper.map(subdistrict, SubdistrictDTO.class);

		Response<SubdistrictDTO> apiResponse = Response.<SubdistrictDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Subdistrict Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubdistrict(@PathVariable UUID id){

		subdistrictService.deleteSubdistrict(id);

		return ResponseEntity.noContent().build();
	}

}
