package com.adrasha.auth.service.impl;

import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.exception.UserNotFoundException;
import com.adrasha.auth.model.Role;
import com.adrasha.auth.model.User;
import com.adrasha.auth.repository.UserRepository;
import com.adrasha.auth.service.RoleService;

public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO addRole(UUID userId, Role role) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		
		Set<Role> updatedRoles = user.getRoles();
		updatedRoles.add(role);
		
		user.setRoles(updatedRoles);
		
		return modelMapper.map(userRepository.save(user), UserDTO.class);
	}

	@Override
	public UserDTO removeRole(UUID userId, Role role) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		
		Set<Role> updatedRoles = user.getRoles();
		updatedRoles.remove(role);
		
		user.setRoles(updatedRoles);
		
		return modelMapper.map(userRepository.save(user), UserDTO.class);
	}


}
