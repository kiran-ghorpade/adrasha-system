package com.adrasha.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.config.AdminInitializer;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.ErrorResponse;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.dto.core.ValidationErrorResponse;
import com.adrasha.auth.service.AuthService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Authentication")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('USER')")
public class UserDetailsController {

    @SuppressWarnings("unused")
	private final AdminInitializer adminInitializer;

	@Autowired
	private AuthService authService;
	

    UserDetailsController(AdminInitializer adminInitializer) {
        this.adminInitializer = adminInitializer;
    }
    
	@GetMapping("/users/me")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JwtUser.class)))
	@PreAuthorize("hasAnyRole('USER')")
	public JwtUser getCurrentUser(Authentication authentication) {

		return (JwtUser) authentication.getPrincipal();
		
	}

	@PostMapping("/resetPassword")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public UserDTO resetPassword(Authentication authentication, @RequestBody PasswordResetRequest passwordResetRequest) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		System.err.println(authentication.getAuthorities());
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		
		return authService.resetPassword(user, passwordResetRequest);

	}
	

	@DeleteMapping("/users/me")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<Void> deleteCurrentUser(Authentication authentication) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		authService.deleteCurrentUser(user);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/users/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@PreAuthorize("hasAnyRole('ADMIN','SYSTEM')")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

		authService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

}
