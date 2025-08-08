package com.adrasha.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.dto.filter.UserFilterDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.model.Role;
import com.adrasha.user.dto.user.UserUpdateDTO;

public interface UserService {

	Page<UserResponseDTO> getUserPage(UserFilterDTO filterDTO, Pageable pageable);
	
	Long getUserCount(UserFilterDTO filterDTO);
    
    UserResponseDTO getUser(UUID id) throws NotFoundException;
    
    UserResponseDTO createUser(UserResponseDTO user) throws AlreadyExistsException;
    
    UserResponseDTO updateUser(UUID userId, UserUpdateDTO updateDTO) throws NotFoundException;
    
    UserResponseDTO deleteUser(UUID userId) throws NotFoundException;

	void removeRoleFromUser(UUID id, Role role);
 }
