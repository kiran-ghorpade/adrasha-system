package com.adrasha.data.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
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
import com.adrasha.core.dto.filter.MemberDataFilterDTO;
import com.adrasha.core.dto.page.MemberDataPageResponseDTO;
import com.adrasha.core.dto.response.MemberDataResponseDTO;
import com.adrasha.data.member.dto.MemberCreateDTO;
import com.adrasha.data.member.dto.MemberUpdateDTO;
import com.adrasha.data.model.Family;
import com.adrasha.data.model.Member;
import com.adrasha.data.service.FamilyDataService;
import com.adrasha.data.service.MemberDataService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data/members")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "MemberData")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('ASHA','SYSTEM')")
public class MemberDataController {
	
	private final MemberDataService memberService;
	private final FamilyDataService familyService;
	private final ModelMapper mapper;


	// Get All Members	
	@GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberDataPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Page<MemberDataResponseDTO> getMemberPage(
			MemberDataFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Member searchTerms = mapper.map(filterDTO, Member.class);

		Example<Member> exampleMember = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		Page<Member> memberPages = memberService.getMemberPage(exampleMember, pageable);
		
		Page<MemberDataResponseDTO> dto = memberPages.map(member -> mapper.map(member, MemberDataResponseDTO.class));
	
		return dto;
	}
	
	// Get Count
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Long getMemberCount(MemberDataFilterDTO filterDTO){
		
		Member searchTerms = mapper.map(filterDTO, Member.class);
		
		Example<Member> exampleMember = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Long count = memberService.getMemberCount(exampleMember);
				
		return count;
	}
	
	
	// Get a Member
	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberDataResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public MemberDataResponseDTO getMember(@PathVariable UUID id){
		
		Member member = memberService.getMember(id);
		return mapper.map(member, MemberDataResponseDTO.class);
	}
	
	// Create new Member
	@PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MemberDataResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<MemberDataResponseDTO> createMember(@Valid @RequestBody MemberCreateDTO memberDTO){
		
		Family family = familyService.getFamily(memberDTO.getFamilyId());
		Member member = mapper.map(memberDTO, Member.class);
		member.setFamilyId(family.getId());
		
		member = memberService.createMember(member);
		MemberDataResponseDTO dto = mapper.map(member, MemberDataResponseDTO.class);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(dto);
	}

	// Update a Member
	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberDataResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public MemberDataResponseDTO udpateMember(
			@PathVariable UUID id,
			@Valid @RequestBody MemberUpdateDTO updatedMember
			){
		Family family = familyService.getFamily(updatedMember.getFamilyId());
		Member member = mapper.map(updatedMember, Member.class);
		member.setFamilyId(family.getId());
		
		member = memberService.updateMember(id, member);
		return mapper.map(member, MemberDataResponseDTO.class);

	}
	
	// Delete a Member	
	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<Void> deleteMember(@PathVariable UUID id){
		
		memberService.deleteMember(id);

		return ResponseEntity.noContent().build();
	}
}
