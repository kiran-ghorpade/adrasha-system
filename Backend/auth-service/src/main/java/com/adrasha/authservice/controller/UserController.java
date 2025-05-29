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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.authservice.dto.ApiResponse;
import com.adrasha.authservice.dto.ErrorResponse;
import com.adrasha.authservice.dto.JwtUser;
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
@RequestMapping("/users")
@SecurityRequirement(name = "BearerAuthentication")
@Secured({"USER", "ADMIN"})
@ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
})
public class UserController {
	
		@Autowired
		private UserService service;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
        @Operation(summary = "Get all users", description = "Returns a user in pageable")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			Page<User> usersPage = service.getAllUsers(pageable);
			Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
			
			ApiResponse<Page<UserDTO>> apiResponse = ApiResponse.<Page<UserDTO>>builder()
					.status(HttpStatus.OK.value())
					.message("List of Users")
					.payload(userDTOPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
        @Operation(summary = "Get a user by ID", description = "Returns a user object based on the provided ID")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<ApiResponse<UserDTO>> getUser(@Parameter(description = "ID of the user to retrieve", required = true) @PathVariable UUID id){
			
			User user = service.getUser(id);
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
					.status(HttpStatus.OK.value())
					.message("User Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/me")
        @Operation(summary = "Get current logged in user", description = "Returns a user object which is currently logged in.")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<ApiResponse<JwtUser>> getCurrentUser(Authentication authentication) {
			
			User user = service.getCurrentUser(authentication);
			
			if(authentication == null) {
				return ResponseEntity.noContent().build();
			}
			
			JwtUser dto = mapper.map(user, JwtUser.class);
			
			ApiResponse<JwtUser> apiResponse = ApiResponse.<JwtUser>builder()
					.status(HttpStatus.OK.value())
					.message("User Details")
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@DeleteMapping("/{id}")
        @Operation(summary = "Delete User", description = "Returns a user object after successful deletion")
		public ResponseEntity<ApiResponse<UserDTO>> deleteUser(@PathVariable UUID id){
			
			service.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
