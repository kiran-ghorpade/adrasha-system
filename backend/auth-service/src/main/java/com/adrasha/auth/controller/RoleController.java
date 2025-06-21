package com.adrasha.auth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.dto.RoleUpdateDTO;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.service.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Role Management (SYSTEM)")
@PreAuthorize("hasRole('SYSTEM')")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModelMapper mapper;

	
	@PutMapping("/roles")
	public JwtUser addRole(@RequestBody RoleUpdateDTO addRoleDTO) {
				
		UserDTO user = roleService.addRole(addRoleDTO.getUserId(), addRoleDTO.getRole());
		return mapper.map(user, JwtUser.class);
	}

	@DeleteMapping("/roles")
	public JwtUser removeRole(@RequestBody RoleUpdateDTO addRoleDTO) {
				
		UserDTO user = roleService.removeRole(addRoleDTO.getUserId(), addRoleDTO.getRole());
		return mapper.map(user, JwtUser.class);
	}
}
