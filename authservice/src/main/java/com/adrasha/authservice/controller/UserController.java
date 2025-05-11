package com.adrasha.authservice.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.authservice.dto.ApiResponse;
import com.adrasha.authservice.dto.UserDTO;
import com.adrasha.authservice.model.User;
import com.adrasha.authservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuthentication")
@RequestMapping("/users")
public class UserController {
	
		@Autowired
		private UserService service;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
        @Operation(summary = "Get all users", description = "Returns a user in pageable")
		@ApiResponses(value = {
			    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			})
		public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.ASC)
				Pageable pageable
				){
	
			Page<User> usersPage = service.getAllUsers(pageable);
			Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
			
			ApiResponse<Page<UserDTO>> apiResponse = ApiResponse.<Page<UserDTO>>builder()
					.status(HttpStatus.OK.toString())
					.message("List of Users")
					.payload(userDTOPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
        @Operation(summary = "Get a user by ID", description = "Returns a user object based on the provided ID")
		@ApiResponses(value = {
			    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			})
		public ResponseEntity<ApiResponse<UserDTO>> getUser(@Parameter(description = "ID of the user to retrieve", required = true) @PathVariable UUID id){
			
			User user = service.getUser(id);
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
					.status(HttpStatus.OK.toString())
					.message("User Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping("/{id}")
        @Operation(summary = "Create new User", description = "Returns a user object after successful registration")
		@ApiResponses(value = {
			    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			})
		public ResponseEntity<ApiResponse<UserDTO>> addUser(@PathVariable UUID id, @RequestBody User user){
			
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
					.status(HttpStatus.OK.toString())
					.message("User Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@DeleteMapping("/{id}")
        @Operation(summary = "Delete User", description = "Returns a user object after successful deletion")
		@ApiResponses(value = {
			    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
			})
		public ResponseEntity<ApiResponse<UserDTO>> deleteUser(@PathVariable UUID id){
			
			service.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
