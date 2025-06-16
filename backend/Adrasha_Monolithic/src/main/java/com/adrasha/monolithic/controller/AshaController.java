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
import com.adrasha.monolithic.exception.AshaNotFoundException;
import com.adrasha.monolithic.model.Asha;
import com.adrasha.monolithic.service.AshaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class AshaController {
	
		@Autowired
		private AshaService service;
		
//		@Autowired
//		private ModelMapper mapper;

		@GetMapping
		public ResponseEntity<ApiResponse<Page<Asha>>> getAllAsha(
			    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
				Pageable pageable
				){
	
			Page<Asha> ashaPage = service.getAllAsha(pageable);
//			Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
			
			ApiResponse<Page<Asha>> apiResponse = ApiResponse.<Page<Asha>>builder()
					.status(HttpStatus.OK.toString())
					.message("List of All Asha")
					.payload(ashaPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping
		public ResponseEntity<ApiResponse<Asha>> addAsha(@Valid @RequestBody Asha newAsha) throws AshaNotFoundException{
			
			Asha asha = service.createAsha(newAsha);
//			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<Asha> apiResponse = ApiResponse.<Asha>builder()
					.status(HttpStatus.OK.toString())
					.message("Asha Details Added. Id : "+ asha.getId())
					.payload(asha)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		
		@GetMapping("/{id}")
		public ResponseEntity<ApiResponse<Asha>> getAsha(@PathVariable UUID id) throws AshaNotFoundException{
			
			Asha asha = service.getAsha(id);
//			UserDTO dto = mapper.map(asha, UserDTO.class);
			
			ApiResponse<Asha> apiResponse = ApiResponse.<Asha>builder()
					.status(HttpStatus.OK.toString())
					.message("Asha Details with id : "+ id)
					.payload(asha)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		

		@PutMapping("/{id}")
		public ResponseEntity<ApiResponse<Asha>> udpateAsha(@PathVariable UUID id, @Valid @RequestBody Asha updatedAsha) throws AshaNotFoundException{
			
			Asha asha = service.updateAsha(id, updatedAsha);
//			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<Asha> apiResponse = ApiResponse.<Asha>builder()
					.status(HttpStatus.OK.toString())
					.message("Asha Details with id "+ id)
					.payload(asha)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<ApiResponse<Asha>> udpateAsha(@PathVariable UUID id) throws AshaNotFoundException{
			
			Asha asha = service.deleteAsha(id);
//			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<Asha> apiResponse = ApiResponse.<Asha>builder()
					.status(HttpStatus.OK.toString())
					.message("Asha Deleted with id "+ id)
					.payload(asha)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}

}
