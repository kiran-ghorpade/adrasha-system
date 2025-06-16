package com.adrasha.monolithic.service;

import java.util.Optional;

import com.adrasha.monolithic.dto.LoginRequest;
import com.adrasha.monolithic.dto.LoginResponse;
import com.adrasha.monolithic.dto.RegistrationRequest;
import com.adrasha.monolithic.dto.UserDTO;

public interface AuthService {
	
	public LoginResponse login(LoginRequest loginRequest);
	
	public Optional<UserDTO> register(RegistrationRequest registrationRequest); 

}
