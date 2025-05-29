package com.adrasha.authservice.service;

import org.springframework.security.core.Authentication;

import com.adrasha.authservice.dto.AuthTokenResponse;
import com.adrasha.authservice.dto.LoginRequest;
import com.adrasha.authservice.dto.PasswordResetRequest;
import com.adrasha.authservice.dto.RegistrationRequest;
import com.adrasha.authservice.dto.UserDTO;
import com.adrasha.authservice.exception.UserAlreadyExistsException;

public interface AuthService {
	
	UserDTO resetPassword(Authentication authentication, PasswordResetRequest passwordResetRequest);
	
	UserDTO register(Authentication authentication, RegistrationRequest registrationRequest)
			throws UserAlreadyExistsException;
			
	AuthTokenResponse login(LoginRequest loginRequest);

}
