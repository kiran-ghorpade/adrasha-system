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
import com.adrasha.monolithic.exception.PregnancyNotFoundException;
import com.adrasha.monolithic.model.Pregnancy;
import com.adrasha.monolithic.service.PregnancyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/Pregnancy")
public class PregnancyController {
	
	@Autowired
	private PregnancyService service;
	
//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Pregnancy>>> getAllPregnancy(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<Pregnancy> PregnancyPage = service.getAllPregnancies(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<Pregnancy>> apiResponse = ApiResponse.<Page<Pregnancy>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Pregnancy")
				.payload(PregnancyPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Pregnancy>> addPregnancy(@Valid @RequestBody Pregnancy newPregnancy) throws PregnancyNotFoundException{
		
		Pregnancy Pregnancy = service.createPregnancy(newPregnancy);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Pregnancy> apiResponse = ApiResponse.<Pregnancy>builder()
				.status(HttpStatus.OK.toString())
				.message("Pregnancy Details Added. Id : "+ Pregnancy.getId())
				.payload(Pregnancy)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Pregnancy>> getPregnancy(@PathVariable UUID id) throws PregnancyNotFoundException{
		
		Pregnancy Pregnancy = service.getPregnancyById(id);
//		UserDTO dto = mapper.map(Pregnancy, UserDTO.class);
		
		ApiResponse<Pregnancy> apiResponse = ApiResponse.<Pregnancy>builder()
				.status(HttpStatus.OK.toString())
				.message("Pregnancy Details with id : "+ id)
				.payload(Pregnancy)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Pregnancy>> udpatePregnancy(@PathVariable UUID id, @Valid @RequestBody Pregnancy updatedPregnancy) throws PregnancyNotFoundException{
		
		Pregnancy Pregnancy = service.updatePregnancy(id, updatedPregnancy);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Pregnancy> apiResponse = ApiResponse.<Pregnancy>builder()
				.status(HttpStatus.OK.toString())
				.message("Pregnancy Details with id "+ id)
				.payload(Pregnancy)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Pregnancy>> udpatePregnancy(@PathVariable UUID id) throws PregnancyNotFoundException{
		
		service.deletePregnancy(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Pregnancy> apiResponse = ApiResponse.<Pregnancy>builder()
				.status(HttpStatus.OK.toString())
				.message("Pregnancy Deleted with id "+ id)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
}
