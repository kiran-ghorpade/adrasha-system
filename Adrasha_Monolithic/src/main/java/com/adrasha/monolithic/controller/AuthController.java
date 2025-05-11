package com.adrasha.monolithic.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.monolithic.dto.ApiResponse;
import com.adrasha.monolithic.dto.LoginRequest;
import com.adrasha.monolithic.dto.LoginResponse;
import com.adrasha.monolithic.dto.RegistrationRequest;
import com.adrasha.monolithic.dto.UserDTO;
import com.adrasha.monolithic.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDTO>> registerUser(
			@RequestBody @Valid RegistrationRequest registrationRequest) {

		Optional<UserDTO> createdUser = authService.register(registrationRequest);

		ApiResponse<UserDTO> apiResponse = ApiResponse.<UserDTO>builder()
				.status(HttpStatus.OK.toString())
				.message("Registration Successful")
				.payload(createdUser.get())
				.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody @Valid LoginRequest loginRequest) {

		LoginResponse loginResponse = authService.login(loginRequest);
		
		ApiResponse<LoginResponse> apiResponse = ApiResponse.<LoginResponse>builder()
				.status(HttpStatus.OK.toString())
				.message("Login Successful")
				.payload(loginResponse)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody String newPassword) {

		return ResponseEntity.ok(null);
	}
}
