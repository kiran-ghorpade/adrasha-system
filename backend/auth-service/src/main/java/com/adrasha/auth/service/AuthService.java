package com.adrasha.auth.service;

import java.util.UUID;

import com.adrasha.auth.dto.AuthTokenResponse;
import com.adrasha.auth.dto.LoginRequest;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.RegistrationRequest;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.exception.UserAlreadyExistsException;
import com.adrasha.auth.model.Role;

public interface AuthService {

	UserDTO register(RegistrationRequest registrationRequest)
			throws UserAlreadyExistsException;
			
	AuthTokenResponse login(LoginRequest loginRequest);

	UserDTO resetPassword(JwtUser jwtUser, PasswordResetRequest passwordResetRequest);

	void deleteCurrentUser(JwtUser jwtUser);

	void deleteUser(UUID id);

	UserDTO updateRole(UUID userId, Role role);

}
