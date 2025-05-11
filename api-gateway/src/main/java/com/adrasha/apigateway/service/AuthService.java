//package com.adrasha.apigateway.service;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.adrasha.apigateway.dto.ApiResponse;
//import com.adrasha.apigateway.dto.LoginRequest;
//import com.adrasha.apigateway.dto.LoginResponse;
//import com.adrasha.apigateway.dto.RegistrationRequest;
//import com.adrasha.apigateway.dto.UserDTO;
//
//import jakarta.validation.Valid;
//
//@Service
//@FeignClient("AUTH-SERVICE")
//public interface AuthService {
//	@PostMapping("/register")
//	public ResponseEntity<ApiResponse<UserDTO>> registerUser(
//			@RequestBody @Valid RegistrationRequest registrationRequest);
//
//	@PostMapping("/login")
//	public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody @Valid LoginRequest loginRequest);
//
//	@GetMapping
//	public boolean validateUser(@RequestParam String token);
//
//	@PostMapping("/resetPassword")
//	public ResponseEntity<?> resetPassword(@RequestBody String newPassword);
//}