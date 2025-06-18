package com.adrasha.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.config.AdminInitializer;
import com.adrasha.auth.dto.RoleUpdateDTO;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.dto.core.Response;
import com.adrasha.auth.dto.swagger.ApiResponseUserDTO;
import com.adrasha.auth.service.AuthService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "UserDetails Management")
public class UserDetailsController {

    @SuppressWarnings("unused")
	private final AdminInitializer adminInitializer;

	@Autowired
	private AuthService authService;

    UserDetailsController(AdminInitializer adminInitializer) {
        this.adminInitializer = adminInitializer;
    }

	@PostMapping("/resetPassword")
	@ApiResponse(content = @Content(schema = @Schema(implementation =  ApiResponseUserDTO.class)))
	@PreAuthorize("hasRole('USER')")
	public Response<UserDTO> resetPassword(Authentication authentication, @RequestBody PasswordResetRequest passwordResetRequest) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		System.err.println(authentication.getAuthorities());
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		
		UserDTO dto = authService.resetPassword(user, passwordResetRequest);

		return Response.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Login Successful")
				.payload(dto).build();

	}

	@PutMapping("/updateRole")
	@ApiResponse(content = @Content(schema = @Schema(implementation =  ApiResponseUserDTO.class)))
	@PreAuthorize("hasRole('ADMIN')")
	public Response<UserDTO> updateRole(@RequestBody RoleUpdateDTO addRoleDTO) {
				
		UserDTO dto = authService.updateRole(addRoleDTO.getUserId(), addRoleDTO.getRole());

		return Response.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Roles Updated")
				.payload(dto).build();

	}

	@DeleteMapping("/users/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteCurrentUser(Authentication authentication) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		authService.deleteCurrentUser(user);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteUserByAdmin(@PathVariable UUID id) {

		authService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

}
