package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.adrasha.user.dto.AddRoleDTO;

//@FeignClient("lb:AUTH-SERVICE/auth")
public interface AuthService {

	ResponseEntity<?> updateRole(AddRoleDTO addRoleDTO);

	ResponseEntity<?> deleteCurrentUser(Authentication authentication);

	ResponseEntity<?> deleteUserByAdmin(UUID id);

}