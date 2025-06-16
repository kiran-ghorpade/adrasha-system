package com.adrasha.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.config.AdminInitializer;
import com.adrasha.auth.dto.AddRoleDTO;
import com.adrasha.auth.dto.ApiResponse;
import com.adrasha.auth.dto.JwtUser;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.ValidationErrorResponse;
import com.adrasha.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
})
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
	@Operation(summary = "Reset Password", description = "Update Current Password with given password")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@SecurityRequirement(name = "BearerAuthentication")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> resetPassword(Authentication authentication, @RequestBody PasswordResetRequest passwordResetRequest) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		System.err.println(authentication.getAuthorities());
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		
		UserDTO dto = authService.resetPassword(user, passwordResetRequest);

		ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Login Successful")
				.payload(dto).build();

		return ResponseEntity.ok(apiResponse);
	}

	@PutMapping("/updateRole")
	@Operation(summary = "Update Role", description = "Update role with given userId")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@SecurityRequirement(name = "BearerAuthentication")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateRole(@RequestBody AddRoleDTO addRoleDTO) {
				
		UserDTO dto = authService.updateRole(addRoleDTO.getUserId(), addRoleDTO.getRole());

		ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Roles Updated")
				.payload(dto).build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/users/me")
	@Operation(summary = "delete security credintials of current logged in user", description = "User will not be able to login again if deleted")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		})
	@SecurityRequirement(name = "BearerAuthentication")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteCurrentUser(Authentication authentication) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		authService.deleteCurrentUser(user);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/users/{id}")
	@Operation(summary = "delete security credintials of user", description = "Admin Role required. User will not be able to login again if deleted")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		})
	@SecurityRequirement(name = "BearerAuthentication")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserByAdmin(@PathVariable UUID id) {

		authService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

}
