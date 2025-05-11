package com.adrasha.monolithic.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.monolithic.dto.ApiResponse;
import com.adrasha.monolithic.exception.MemberNotFoundException;
import com.adrasha.monolithic.model.Member;
import com.adrasha.monolithic.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Member>>> getAllMember(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<Member> MemberPage = service.getAllMembers(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<Member>> apiResponse = ApiResponse.<Page<Member>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Member")
				.payload(MemberPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Member>> addMember(@Valid @RequestBody Member newMember) throws MemberNotFoundException{
		
		Member Member = service.createMember(newMember);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Member> apiResponse = ApiResponse.<Member>builder()
				.status(HttpStatus.OK.toString())
				.message("Member Details Added. Id : "+ Member.getId())
				.payload(Member)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Member>> getMember(@PathVariable UUID id) throws MemberNotFoundException{
		
		Member Member = service.getMember(id);
//		UserDTO dto = mapper.map(Member, UserDTO.class);
		
		ApiResponse<Member> apiResponse = ApiResponse.<Member>builder()
				.status(HttpStatus.OK.toString())
				.message("Member Details with id : "+ id)
				.payload(Member)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Member>> udpateMember(@PathVariable UUID id, @Valid @RequestBody Member updatedMember) throws MemberNotFoundException{
		
		Member Member = service.updateMember(id, updatedMember);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Member> apiResponse = ApiResponse.<Member>builder()
				.status(HttpStatus.OK.toString())
				.message("Member Details with id "+ id)
				.payload(Member)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Member>> udpateMember(@PathVariable UUID id) throws MemberNotFoundException{
		
		Member Member = service.deleteMember(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Member> apiResponse = ApiResponse.<Member>builder()
				.status(HttpStatus.OK.toString())
				.message("Member Deleted with id "+ id)
				.payload(Member)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
}
