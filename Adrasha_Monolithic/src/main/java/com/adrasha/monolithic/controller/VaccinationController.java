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
import com.adrasha.monolithic.exception.VaccinationNotFoundException;
import com.adrasha.monolithic.model.Vaccination;
import com.adrasha.monolithic.service.VaccinationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vaccination")
public class VaccinationController {
	
	@Autowired
	private VaccinationService service;
	
//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Vaccination>>> getAllVaccination(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<Vaccination> VaccinationPage = service.getAllVaccinations(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<Vaccination>> apiResponse = ApiResponse.<Page<Vaccination>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Vaccination")
				.payload(VaccinationPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Vaccination>> addVaccination(@Valid @RequestBody Vaccination newVaccination) throws VaccinationNotFoundException{
		
		Vaccination Vaccination = service.createVaccination(newVaccination);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccination> apiResponse = ApiResponse.<Vaccination>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccination Details Added. Id : "+ Vaccination.getId())
				.payload(Vaccination)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccination>> getVaccination(@PathVariable UUID id) throws VaccinationNotFoundException{
		
		Vaccination Vaccination = service.getVaccinationById(id);
//		UserDTO dto = mapper.map(Vaccination, UserDTO.class);
		
		ApiResponse<Vaccination> apiResponse = ApiResponse.<Vaccination>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccination Details with id : "+ id)
				.payload(Vaccination)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccination>> udpateVaccination(@PathVariable UUID id, @Valid @RequestBody Vaccination updatedVaccination) throws VaccinationNotFoundException{
		
		Vaccination Vaccination = service.updateVaccination(id, updatedVaccination);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccination> apiResponse = ApiResponse.<Vaccination>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccination Details with id "+ id)
				.payload(Vaccination)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccination>> udpateVaccination(@PathVariable UUID id) throws VaccinationNotFoundException{
		
		service.deleteVaccination(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccination> apiResponse = ApiResponse.<Vaccination>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccination Deleted with id "+ id)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
}
