package com.adrasha.data.controller;

import java.net.URI;
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

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.response.dto.MemberResponseDTO;
import com.adrasha.data.member.dto.MemberCreateDTO;
import com.adrasha.data.member.dto.MemberFilterDTO;
import com.adrasha.data.member.dto.MemberUpdateDTO;
import com.adrasha.data.model.Family;
import com.adrasha.data.model.Member;
import com.adrasha.data.service.FamilyDataService;
import com.adrasha.data.service.MemberDataService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/data/members")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Member Management")
@PreAuthorize("hasRole('ASHA')")
public class MemberDataController {
	
	@Autowired
	private MemberDataService memberService;
	
	@Autowired
	private FamilyDataService familyService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public Page<MemberResponseDTO> getAllMembers(
			MemberFilterDTO filterDTO,
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Member searchTerms = mapper.map(filterDTO, Member.class);

		Example<Member> exampleMember = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
		
		Page<Member> memberPages = memberService.getAllMembers(exampleMember, pageable);
		
		Page<MemberResponseDTO> dto = memberPages.map(member -> mapper.map(member, MemberResponseDTO.class));
	
		return dto;
	}
	
	@GetMapping("/{id}")
	public MemberResponseDTO getMember(@PathVariable UUID id){
		
		Member member = memberService.getMember(id);
		return mapper.map(member, MemberResponseDTO.class);
	}
	
	@PostMapping
	public ResponseEntity<MemberResponseDTO> createMember(@Valid @RequestBody MemberCreateDTO memberDTO){
		
		Family family = familyService.getFamily(memberDTO.getFamilyId());
		Member member = mapper.map(memberDTO, Member.class);
		member.setFamilyId(family.getId());
		
		member = memberService.createMember(member);
		MemberResponseDTO dto = mapper.map(member, MemberResponseDTO.class);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		
		return ResponseEntity.created(location).body(dto);
	}

	@PutMapping("/{id}")
	public MemberResponseDTO udpateMember(
			@PathVariable UUID id,
			@Valid @RequestBody MemberUpdateDTO updatedMember
			){
		Family family = familyService.getFamily(updatedMember.getFamilyId());
		Member member = mapper.map(updatedMember, Member.class);
		member.setFamilyId(family.getId());
		
		member = memberService.updateMember(id, member);
		return mapper.map(member, MemberResponseDTO.class);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable UUID id){
		
		memberService.deleteMember(id);

		return ResponseEntity.noContent().build();
	}
}
