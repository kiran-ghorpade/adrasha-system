package com.adrasha.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.dto.AuthTokenResponse;
import com.adrasha.auth.dto.LoginRequest;
import com.adrasha.auth.dto.RegistrationRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.ValidationErrorResponse;
import com.adrasha.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@ApiResponses(value = {
	    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
})
@Tag(name = "Authentication Management")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	@Operation(summary = "Register New User", description = "Returns a user after registration")
	public ResponseEntity<UserDTO> registerUser(
			@RequestBody @Valid RegistrationRequest registrationRequest) {

		UserDTO createdUser = authService.register(registrationRequest);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PostMapping("/login")
	@Operation(summary = "Login for user", description = "Returns a user object after successful login")
	public AuthTokenResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {

		return authService.login(loginRequest);

	}

	
}
