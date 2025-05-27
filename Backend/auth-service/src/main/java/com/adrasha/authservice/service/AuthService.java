package com.adrasha.authservice.service;

import com.adrasha.authservice.dto.AuthTokenResponse;
import com.adrasha.authservice.dto.LoginRequest;
import com.adrasha.authservice.dto.PasswordResetRequest;
import com.adrasha.authservice.dto.RegistrationRequest;
import com.adrasha.authservice.dto.UserDTO;

public interface AuthService {
	
	public AuthTokenResponse login(LoginRequest loginRequest);
	
	public UserDTO register(RegistrationRequest registrationRequest); 
		
	public UserDTO resetPassword(PasswordResetRequest passwordResetRequest);

}
