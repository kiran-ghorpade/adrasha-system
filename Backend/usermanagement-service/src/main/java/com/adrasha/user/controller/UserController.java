package com.adrasha.user.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.JwtUser;
import com.adrasha.core.dto.Response;
import com.adrasha.user.dto.UserDTO;
import com.adrasha.user.dto.UserRequestDTO;
import com.adrasha.user.model.User;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
})
@Tag(name = "User Management")
public class UserController {
	
		@Autowired
		private UserService userService;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
        @Operation(summary = "Get all users", description = "Returns a user in pageable")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<Page<UserDTO>>> getAllUsers(
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			Page<User> usersPage = userService.getAllUsers(pageable);
			Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
			
			Response<Page<UserDTO>> apiResponse = Response.<Page<UserDTO>>builder()
					.status(HttpStatus.OK.value())
					.message("List of Users")
					.payload(userDTOPage)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
        @Operation(summary = "Get a user by ID", description = "Returns a user object based on the provided ID")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<UserDTO>> getUser(@Parameter(description = "ID of the user to retrieve", required = true) @PathVariable UUID id){
			
			User user = userService.getUser(id);
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			Response<UserDTO> apiResponse = Response.<UserDTO>builder()
					.status(HttpStatus.OK.value())
					.message("User Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		
		@GetMapping("/me")
        @Operation(summary = "Get current logged in user", description = "Returns a user object which is currently logged in.")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<Response<JwtUser>> getCurrentUser(Authentication authentication) {
			
			JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
			
			User user = userService.getCurrentUser(jwtUser);

			JwtUser dto = mapper.map(user, JwtUser.class);
			
			Response<JwtUser> apiResponse = Response.<JwtUser>builder()
					.status(HttpStatus.OK.value())
					.message("User Details")
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping
        @Operation(summary = "Create new user", description = "Returns created user")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<UserDTO>> createRoleRequest(@Valid @RequestBody UserRequestDTO userRequestDto){
			
			User user = mapper.map(userRequestDto, User.class);
			user = userService.createUser(user);
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			Response<UserDTO> apiResponse = Response.<UserDTO>builder()
					.status(HttpStatus.CREATED.value())
					.message("User created with id "+ dto.getId())
					.payload(dto)
					.build();
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
			
			return ResponseEntity.created(location).body(apiResponse);
		}
		
		@PutMapping("/{id}")
        @Operation(summary = "Update a user by ID", description = "Returns a updated user object based on the provided ID")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<UserDTO>> udpatedUser(@Parameter(description = "ID of the user to retrieve", required = true) @PathVariable UUID id, UserRequestDTO updatedUser){
			
			User user = mapper.map(updatedUser, User.class);
			user = userService.updateUser(id, user);
			UserDTO dto = mapper.map(user, UserDTO.class);
			
			Response<UserDTO> apiResponse = Response.<UserDTO>builder()
					.status(HttpStatus.OK.value())
					.message("User Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}

		@DeleteMapping("/{id}")
        @Operation(summary = "Delete User", description = "Returns a user object after successful deletion")
		public ResponseEntity<Response<UserDTO>> deleteUser(@PathVariable UUID id){
			
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
