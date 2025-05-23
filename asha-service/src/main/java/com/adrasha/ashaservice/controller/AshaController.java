package com.adrasha.ashaservice.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
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

import com.adrasha.ashaservice.dto.AshaDTO;
import com.adrasha.ashaservice.dto.AshaDetailsDTO;
import com.adrasha.ashaservice.dto.AshaRegistrationDTO;
import com.adrasha.ashaservice.exception.AshaAlreadyExistsException;
import com.adrasha.ashaservice.exception.AshaNotFoundException;
import com.adrasha.ashaservice.model.Asha;
import com.adrasha.ashaservice.service.AshaService;
import com.adrasha.core.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/asha")
public class AshaController {

	@Autowired
	private AshaService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
    @Operation(summary = "Get all users", description = "Returns a user in pageable")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AshaDTO.class))),
		})
	public ResponseEntity<ApiResponse<Page<AshaDTO>>> getAllAsha(
		    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){

		Page<Asha> ashaPage = service.getAllAsha(pageable);
		Page<AshaDTO> ashaDTOPage = ashaPage.map(asha -> mapper.map(asha, AshaDTO.class));
		
		ApiResponse<Page<AshaDTO>> apiResponse = ApiResponse.<Page<AshaDTO>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All Asha")
				.payload(ashaDTOPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	@RolesAllowed("ROLE_ASHA")
	public ResponseEntity<ApiResponse<AshaDTO>> addAsha(@Valid @RequestBody AshaRegistrationDTO request) throws AshaAlreadyExistsException{
		
		Asha newAsha = mapper.map(request, Asha.class);
		
		Asha savedAsha= service.createAsha(newAsha);
		
		AshaDTO dto = mapper.map(savedAsha, AshaDTO.class);
		
		ApiResponse<AshaDTO> apiResponse = ApiResponse.<AshaDTO>builder()
				.status(HttpStatus.OK.toString())
				.message("Asha Details Added. Id : "+ dto.getId())
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AshaDetailsDTO>> getAsha(@PathVariable UUID id) throws AshaNotFoundException{
		
		Asha Asha = service.getAsha(id);
		AshaDetailsDTO dto = mapper.map(Asha, AshaDetailsDTO.class);
		
		ApiResponse<AshaDetailsDTO> apiResponse = ApiResponse.<AshaDetailsDTO>builder()
				.status(HttpStatus.OK.toString())
				.message("Asha Details with id : "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	@RolesAllowed("ROLE_ASHA")
	public ResponseEntity<ApiResponse<AshaDTO>> udpateAsha(@PathVariable UUID id, @Valid @RequestBody AshaDTO updatedAsha) throws AshaNotFoundException{
		
		Asha asha = mapper.map(updatedAsha, Asha.class);
		asha = service.updateAsha(id, asha);
		AshaDTO dto = mapper.map(asha, AshaDTO.class);
		
		ApiResponse<AshaDTO> apiResponse = ApiResponse.<AshaDTO>builder()
				.status(HttpStatus.OK.toString())
				.message("Asha Details with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	@RolesAllowed("ROLE_ASHA")
	public ResponseEntity<ApiResponse<AshaDTO>> deleteAsha(@PathVariable UUID id) throws AshaNotFoundException{
		
		Asha asha = service.deleteAsha(id);
		AshaDTO dto = mapper.map(asha, AshaDTO.class);
		
		ApiResponse<AshaDTO> apiResponse = ApiResponse.<AshaDTO>builder()
				.status(HttpStatus.OK.toString())
				.message("Asha Deleted with id "+ id)
				.payload(dto)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
}
