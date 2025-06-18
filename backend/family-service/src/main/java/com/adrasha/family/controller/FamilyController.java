package com.adrasha.family.controller;

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

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.Response;
import com.adrasha.family.dto.family.FamilyFilterDTO;
import com.adrasha.family.dto.family.FamilyRegistrationDTO;
import com.adrasha.family.dto.family.FamilyResponseDTO;
import com.adrasha.family.dto.family.FamilyUpdateDTO;
import com.adrasha.family.model.Family;
import com.adrasha.family.model.Member;
import com.adrasha.family.service.FamilyRegistrationService;
import com.adrasha.family.service.FamilyService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/families")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Family Management")
public class FamilyController {

	@Autowired
	private FamilyService familyService;

	@Autowired
	private FamilyRegistrationService familyRegistrationService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ASHA','ADMIN')")
	public ResponseEntity<Response<Page<FamilyResponseDTO>>> getAllFamilies(
			FamilyFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Family searchTerms = mapper.map(filterDTO, Family.class);
		
		Example<Family> exampleFamily = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Page<Family> familyPages = familyService.getAllFamilies(exampleFamily, pageable);
		
		Page<FamilyResponseDTO> FamilyResponseDTOPage = familyPages.map(family -> mapper.map(family, FamilyResponseDTO.class));
		
		Response<Page<FamilyResponseDTO>> apiResponse = Response.<Page<FamilyResponseDTO>>builder()
				.status(HttpStatus.OK.value())
				.message("List of families")
				.payload(FamilyResponseDTOPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<FamilyResponseDTO>> getFamily(@Parameter(description = "ID of the family to retrieve", required = true) @PathVariable UUID id){
		
		Family request = familyService.getFamily(id);
		FamilyResponseDTO dto = mapper.map(request, FamilyResponseDTO.class);
		
		Response<FamilyResponseDTO> apiResponse = Response.<FamilyResponseDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Family Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping("/register")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<FamilyResponseDTO>> createFamily(@Valid @RequestBody FamilyRegistrationDTO familyRegistrationDTO){
		
		Family family = mapper.map(familyRegistrationDTO.getFamily(), Family.class);
		Member headMember = mapper.map(familyRegistrationDTO.getHeadMember(), Member.class);
		
		Family newfamily = familyRegistrationService.registerFamilyWithHead(family, headMember);
		FamilyResponseDTO dto = mapper.map(newfamily, FamilyResponseDTO.class);
		
		Response<FamilyResponseDTO> apiResponse = Response.<FamilyResponseDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("Family created with id "+ dto.getId())
				.payload(dto)
				.build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(apiResponse);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Response<FamilyResponseDTO>> udpateFamily(
			@Parameter(description = "ID of the family to be updated", required = true)
			@PathVariable UUID id,
			@Valid @RequestBody FamilyUpdateDTO updatedFamily
			){
		
		Family family = mapper.map(updatedFamily, Family.class);
		family = familyService.updateFamily(id, family);
		FamilyResponseDTO dto = mapper.map(family, FamilyResponseDTO.class);
		
		Response<FamilyResponseDTO> apiResponse = Response.<FamilyResponseDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Updated Family Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ASHA')")
	public ResponseEntity<Void> deleteRoleRequest(@PathVariable UUID id){
		
		familyService.deleteFamily(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
