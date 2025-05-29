package com.adrasha.familyservice.controller;

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

import com.adrasha.familyservice.dto.ApiResponse;
import com.adrasha.familyservice.dto.FamilyRegistrationDTO;
import com.adrasha.familyservice.exception.FamilyNotFoundException;
import com.adrasha.familyservice.model.Family;
import com.adrasha.familyservice.service.FamilyRegistrationService;
import com.adrasha.familyservice.service.FamilyService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/famliy")
public class FamilyController {

	@Autowired
	private FamilyService service;

	@Autowired
	private FamilyRegistrationService registrationService;

//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<AiResponse<Page<Family>>> getAllFamily(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<Family> FamilyPage = service.getAllFamilies(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<Family>> apiResponse = ApiResponse.<Page<Family>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Family")
				.payload(FamilyPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Family>> addFamily(@Valid @RequestBody FamilyRegistrationDTO request) throws FamilyNotFoundException{
		
		Family Family = registrationService.registerFamilyWithHead(request.getMember(), request.getFamily());
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Family> apiResponse = ApiResponse.<Family>builder()
				.status(HttpStatus.OK.toString())
				.message("Family Details Added. Id : "+ Family.getId())
				.payload(Family)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Family>> getFamily(@PathVariable UUID id) throws FamilyNotFoundException{
		
		Family Family = service.getFamily(id);
//		UserDTO dto = mapper.map(Family, UserDTO.class);
		
		ApiResponse<Family> apiResponse = ApiResponse.<Family>builder()
				.status(HttpStatus.OK.toString())
				.message("Family Details with id : "+ id)
				.payload(Family)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Family>> udpateFamily(@PathVariable UUID id, @Valid @RequestBody Family updatedFamily) throws FamilyNotFoundException{
		
		Family Family = service.updateFamily(id, updatedFamily);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Family> apiResponse = ApiResponse.<Family>builder()
				.status(HttpStatus.OK.toString())
				.message("Family Details with id "+ id)
				.payload(Family)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Family>> udpateFamily(@PathVariable UUID id) throws FamilyNotFoundException{
		
		Family Family = service.deleteFamily(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Family> apiResponse = ApiResponse.<Family>builder()
				.status(HttpStatus.OK.toString())
				.message("Family Deleted with id "+ id)
				.payload(Family)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
}
