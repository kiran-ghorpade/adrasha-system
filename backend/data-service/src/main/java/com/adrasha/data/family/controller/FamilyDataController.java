package com.adrasha.data.family.controller;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.dto.filter.FamilyDataFilterDTO;
import com.adrasha.core.dto.page.FamilyPageResponseDTO;
import com.adrasha.core.dto.response.FamilyDataResponseDTO;
import com.adrasha.data.family.dto.FamilyRegistrationDTO;
import com.adrasha.data.family.dto.FamilyUpdateDTO;
import com.adrasha.data.family.model.Family;
import com.adrasha.data.family.service.FamilyDataService;
import com.adrasha.data.member.model.Member;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/data/families")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "FamilyData")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('ASHA', 'SYSTEM')")
public class FamilyDataController {

	@Autowired
	private FamilyDataService familyService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Page<FamilyDataResponseDTO> getAllFamilies(
			FamilyDataFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Family searchTerms = mapper.map(filterDTO, Family.class);
		
		Example<Family> exampleFamily = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Page<Family> familyPages = familyService.getAllFamilies(exampleFamily, pageable);
		
		Page<FamilyDataResponseDTO> dto = familyPages.map(family -> mapper.map(family, FamilyDataResponseDTO.class));
		
		return dto;
	}
	
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Map<String, Long> getTotalCount(FamilyDataFilterDTO filterDTO) {
		Family filter = mapper.map(filterDTO, Family.class);

		Example<Family> example = Example.of(filter, ExampleMatcherUtils.getDefaultMatcher());

		long total = familyService.getCount(example);
		return Map.of("count", total);
	}
	
	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyDataResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public FamilyDataResponseDTO getFamily(@PathVariable UUID id){
		
		Family request = familyService.getFamily(id);
		return mapper.map(request, FamilyDataResponseDTO.class);
		
	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = FamilyDataResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<FamilyDataResponseDTO> createFamily(@Valid @RequestBody FamilyRegistrationDTO familyRegistrationDTO){
		
		Family family = mapper.map(familyRegistrationDTO.getFamily(), Family.class);
		Member headMember = mapper.map(familyRegistrationDTO.getHeadMember(), Member.class);
		
		Family newfamily = familyService.createFamily(family, headMember);
		FamilyDataResponseDTO dto = mapper.map(newfamily, FamilyDataResponseDTO.class);

		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(dto);
	}
	
	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyDataResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public FamilyDataResponseDTO updateFamily(
			@PathVariable UUID id,
			@Valid @RequestBody FamilyUpdateDTO updatedFamily
			){
		
		Family family = mapper.map(updatedFamily, Family.class);
		family = familyService.updateFamily(id, family);
		return mapper.map(family, FamilyDataResponseDTO.class);

		}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> deleteFamily(@PathVariable UUID id){
		
		familyService.deleteFamily(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
