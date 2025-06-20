package com.adrasha.user.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.exception.UnAuthorizedException;
import com.adrasha.core.response.dto.UserResponseDTO;
import com.adrasha.user.model.User;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users/me")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "User Management")
public class CurrentUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	@PreAuthorize("hasRole('USER')")
	public UserResponseDTO getCurrentUser(Authentication authentication) {

		if (authentication == null) {
			throw new UnAuthorizedException();
		}

		// Assuming Principle is UUID , if principle changes, please change this code
		UUID id = UUID.fromString(authentication.getPrincipal().toString());

		User user = userService.getUser(id);

		return mapper.map(user, UserResponseDTO.class);

	}
}
