package com.adrasha.ncd.controller;

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

import com.adrasha.ncd.dto.ApiResponse;
import com.adrasha.ncd.exception.VaccineNotFoundException;
import com.adrasha.ncd.model.Vaccine;
import com.adrasha.ncd.service.VaccineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vaccine")
public class VaccineController {
	
	@Autowired
	private VaccineService service;
	
//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Vaccine>>> getAllVaccine(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<Vaccine> VaccinePage = service.getAllVaccines(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<Vaccine>> apiResponse = ApiResponse.<Page<Vaccine>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Vaccine")
				.payload(VaccinePage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Vaccine>> addVaccine(@Valid @RequestBody Vaccine newVaccine) throws VaccineNotFoundException{
		
		Vaccine Vaccine = service.createVaccine(newVaccine);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccine> apiResponse = ApiResponse.<Vaccine>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccine Details Added. Id : "+ Vaccine.getId())
				.payload(Vaccine)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccine>> getVaccine(@PathVariable UUID id) throws VaccineNotFoundException{
		
		Vaccine Vaccine = service.getVaccine(id);
//		UserDTO dto = mapper.map(Vaccine, UserDTO.class);
		
		ApiResponse<Vaccine> apiResponse = ApiResponse.<Vaccine>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccine Details with id : "+ id)
				.payload(Vaccine)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccine>> udpateVaccine(@PathVariable UUID id, @Valid @RequestBody Vaccine updatedVaccine) throws VaccineNotFoundException{
		
		Vaccine Vaccine = service.updateVaccine(id, updatedVaccine);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccine> apiResponse = ApiResponse.<Vaccine>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccine Details updated with id "+ id)
				.payload(Vaccine)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Vaccine>> udpateVaccine(@PathVariable UUID id) throws VaccineNotFoundException{
		
		service.deleteVaccine(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<Vaccine> apiResponse = ApiResponse.<Vaccine>builder()
				.status(HttpStatus.OK.toString())
				.message("Vaccine Deleted with id "+ id)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
}
