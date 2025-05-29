package com.adrasha.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.authservice.dto.ApiResponse;
import com.adrasha.authservice.dto.AuthTokenResponse;
import com.adrasha.authservice.dto.ErrorResponse;
import com.adrasha.authservice.dto.LoginRequest;
import com.adrasha.authservice.dto.PasswordResetRequest;
import com.adrasha.authservice.dto.RegistrationRequest;
import com.adrasha.authservice.dto.UserDTO;
import com.adrasha.authservice.dto.ValidationErrorResponse;
import com.adrasha.authservice.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@ApiResponses(value = {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
})
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	@Operation(summary = "Register New User", description = "Returns a user after registration")
	public ResponseEntity<ApiResponse<UserDTO>> registerUser(
			Authentication authentication,
			@RequestBody @Valid RegistrationRequest registrationRequest) {

		UserDTO createdUser = authService.register(authentication, registrationRequest);

		ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Registration Successful")
				.payload(createdUser).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	@PostMapping("/login")
	@Operation(summary = "Login for user", description = "Returns a user object after successful login")
	public ResponseEntity<ApiResponse<AuthTokenResponse>> loginUser(@RequestBody @Valid LoginRequest loginRequest) {

		AuthTokenResponse tokenResponse = authService.login(loginRequest);

		ApiResponse<AuthTokenResponse> apiResponse = ApiResponse.<AuthTokenResponse>builder()
				.status(HttpStatus.OK.value())
				.message("Login Successful")
				.payload(tokenResponse).build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/resetPassword")
	@Operation(summary = "Reset Password", description = "Update Current Password with given password")
	@ApiResponses(value = {
		    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	@SecurityRequirement(name = "BearerAuthentication")
	public ResponseEntity<?> resetPassword(Authentication authentication, @RequestBody PasswordResetRequest passwordResetRequest) {

		UserDTO dto = authService.resetPassword(authentication, passwordResetRequest);

		ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
				.status(HttpStatus.OK.value())
				.message("Login Successful")
				.payload(dto).build();

		return ResponseEntity.ok(apiResponse);
	}

}
