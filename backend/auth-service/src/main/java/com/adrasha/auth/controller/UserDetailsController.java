package com.adrasha.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.config.AdminInitializer;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.service.AuthService;

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
	@PreAuthorize("hasRole('USER')")
	public UserDTO resetPassword(Authentication authentication, @RequestBody PasswordResetRequest passwordResetRequest) {

		JwtUser user = (JwtUser) authentication.getPrincipal();
		System.err.println(authentication.getAuthorities());
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		
		return authService.resetPassword(user, passwordResetRequest);

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
