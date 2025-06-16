package com.adrasha.auth.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.adrasha.auth.dto.JwtUser;
import com.adrasha.auth.dto.PasswordResetRequest;
import com.adrasha.auth.dto.UserDTO;

public interface UserService extends UserDetailsService{

	public UserDTO resetPassword(JwtUser jwtUser, PasswordResetRequest passwordResetRequest);

	public void deleteCurrentUser(JwtUser jwtUser);

	public void deleteUser(UUID id);

}
