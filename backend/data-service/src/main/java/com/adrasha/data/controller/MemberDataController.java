package com.adrasha.data.controller;

import java.net.URI;
import java.util.List;
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
import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.core.dto.page.MemberDataPageResponseDTO;
import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.dto.response.MemberResponseDTO;
import com.adrasha.data.member.dto.MemberCreateDTO;
import com.adrasha.data.member.dto.MemberUpdateDTO;
import com.adrasha.data.service.MemberDataService;

import io.swagger.v3.oas.annotations.Hidden;
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

	// Get All Members	
	@GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberDataPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Page<MemberResponseDTO> getMemberPage(
			MemberFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		return memberService.getMemberPage(filterDTO, pageable);
	}
	
	
	// Get All Members Data For Report
	@GetMapping("/list")
	@Hidden
	public List<MemberReportDTO> getMemberList(MemberFilterDTO filterDTO) {

		return memberService.getMemberList(filterDTO);
	}
	
	// Get Count
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Long getMemberCount(MemberFilterDTO filterDTO){

		Long count = memberService.getMemberCount(filterDTO);
				
		return count;
	}
	
	
	// Get a Member
	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public MemberResponseDTO getMember(@PathVariable UUID id){
		
		return memberService.getMember(id);
	}
	
	// Create new Member
	@PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = MemberResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<MemberResponseDTO> createMember(@Valid @RequestBody MemberCreateDTO createDTO){
		
		MemberResponseDTO dto = memberService.createMember(createDTO);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(dto);
	}

	// Update a Member
	@PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MemberResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public MemberResponseDTO udpateMember(
			@PathVariable UUID id,
			@Valid @RequestBody MemberUpdateDTO updatedMember
			){
		
		return memberService.updateMember(id, updatedMember);
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
