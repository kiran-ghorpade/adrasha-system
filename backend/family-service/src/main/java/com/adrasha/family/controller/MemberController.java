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

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.Response;
import com.adrasha.family.dto.member.MemberCreateDTO;
import com.adrasha.family.dto.member.MemberFilterDTO;
import com.adrasha.family.dto.member.MemberResponseDTO;
import com.adrasha.family.dto.member.MemberUpdateDTO;
import com.adrasha.family.model.Member;
import com.adrasha.family.service.MemberService;

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
@RequestMapping("/members")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
})
@Tag(name = "Member Management")
@PreAuthorize("hasRole('USER')")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
    @Operation(summary = "Get all members", description = "Returns a members in pageable")
	@PreAuthorize("hasAnyRole('ASHA','ADMIN')")
	public ResponseEntity<Response<Page<MemberResponseDTO>>> getAllMembers(
			MemberFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Member searchTerms = mapper.map(filterDTO, Member.class);

		Example<Member> exampleMember = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		Page<Member> memberPages = memberService.getAllMembers(exampleMember, pageable);
		
		Page<MemberResponseDTO> MemberResponseDTOPage = memberPages.map(member -> mapper.map(member, MemberResponseDTO.class));
		
		Response<Page<MemberResponseDTO>> apiResponse = Response.<Page<MemberResponseDTO>>builder()
				.status(HttpStatus.OK.value())
				.message("List of members")
				.payload(MemberResponseDTOPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/{id}")
    @Operation(summary = "Get a member by ID", description = "Returns a member based on the provided ID")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<MemberResponseDTO>> getMember(@Parameter(description = "ID of the member to retrieve", required = true) @PathVariable UUID id){
		
		Member member = memberService.getMember(id);
		MemberResponseDTO dto = mapper.map(member, MemberResponseDTO.class);
		
		Response<MemberResponseDTO> apiResponse = Response.<MemberResponseDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Member Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
    @Operation(summary = "Create new RoleRequest", description = "Returns created role request")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<MemberResponseDTO>> createFamily(@Valid @RequestBody MemberCreateDTO memberDTO){
		
		Member member = mapper.map(memberDTO, Member.class);
		member = memberService.createMember(member);
		MemberResponseDTO dto = mapper.map(member, MemberResponseDTO.class);
		
		Response<MemberResponseDTO> apiResponse = Response.<MemberResponseDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("Member created with id "+ dto.getId())
				.payload(dto)
				.build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(apiResponse);
	}

	@PutMapping("/{id}")
    @Operation(summary = "Update a member by ID", description = "Returns a updated member based on the provided ID")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<MemberResponseDTO>> udpateMember(
			@Parameter(description = "ID of the member to be updated", required = true)
			@PathVariable UUID id,
			@Valid @RequestBody MemberUpdateDTO updatedMember
			){
		
		Member member = mapper.map(updatedMember, Member.class);
		member = memberService.updateMember(id, member);
		MemberResponseDTO dto = mapper.map(member, MemberResponseDTO.class);
		
		Response<MemberResponseDTO> apiResponse = Response.<MemberResponseDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Updated Member Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Delete Member", description = "deletes member and return nothing if successful deletion")
	public ResponseEntity<Void> udpateMember(@PathVariable UUID id){
		
		memberService.deleteMember(id);

		return ResponseEntity.noContent().build();
	}
}
