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
import com.adrasha.masterdata.dto.LocalityDTO;
import com.adrasha.masterdata.model.Locality;
import com.adrasha.masterdata.service.LocalityService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/localities")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Locality Management")
public class LocalityController {

	@Autowired
	private LocalityService localityService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Page<Locality>>> getAllLocalities(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable,
			@RequestParam(required = false)
			UUID subdistrictId
			){

		Page<Locality> page = localityService.getAllLocalities(pageable, subdistrictId);

		Response<Page<Locality>> apiResponse = Response.<Page<Locality>>builder()
				.status(HttpStatus.OK.value())
				.message("List of Localities")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<LocalityDTO>> getlocality(@Parameter(description = "ID of the locality to retrieve", required = true) @PathVariable UUID id){

		Locality locality = localityService.getLocality(id);
		LocalityDTO dto = mapper.map(locality, LocalityDTO.class);

		Response<LocalityDTO> apiResponse = Response.<LocalityDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Locality Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<LocalityDTO>> createLocality(@Valid @RequestBody LocalityDTO newLocality){

		Locality locality = mapper.map(newLocality, Locality.class);
		locality = localityService.createLocality(locality);
		LocalityDTO dto = mapper.map(locality, LocalityDTO.class);

		Response<LocalityDTO> apiResponse = Response.<LocalityDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("Locality created with id "+ dto.getId())
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
	public ResponseEntity<Response<LocalityDTO>> udpateLocality(@PathVariable UUID id, @Valid @RequestBody LocalityDTO updatedLocality){

		Locality locality = mapper.map(updatedLocality, Locality.class);
		locality = localityService.updateLocality(id, locality);
		LocalityDTO dto = mapper.map(locality, LocalityDTO.class);

		Response<LocalityDTO> apiResponse = Response.<LocalityDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Locality Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLocality(@PathVariable UUID id){

		localityService.deleteLocality(id);

		return ResponseEntity.noContent().build();
	}

}
