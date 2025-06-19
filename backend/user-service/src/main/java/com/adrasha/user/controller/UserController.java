package com.adrasha.user.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.exception.UnAuthorizedException;
import com.adrasha.user.dto.user.UserFilterDTO;
import com.adrasha.user.dto.user.UserResponseDTO;
import com.adrasha.user.dto.user.UserUpdateDTO;
import com.adrasha.user.model.User;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "User Management")
public class UserController {
	
		@Autowired
		private UserService userService;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
		@PreAuthorize("hasRole('USER')")
		public Page<UserResponseDTO> getAllUsers(
				UserFilterDTO filterDTO,
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
			
			User searchTerms = mapper.map(filterDTO, User.class);
			
			Example<User> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
	
			Page<User> usersPage = userService.getAllUsers(example, pageable);
			
			Page<UserResponseDTO> dtoPage = usersPage.map(roleRequest -> mapper.map(roleRequest, UserResponseDTO.class));
			
			return dtoPage;
		}
		
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('USER')")
		public UserResponseDTO getUser(@PathVariable UUID id){
			
			User user = userService.getUser(id);
			
			return mapper.map(user, UserResponseDTO.class);
		}
		
		
		@GetMapping("/me")
		@PreAuthorize("hasRole('USER')")
		public UserResponseDTO getCurrentUser(Authentication authentication) {
			
			if(authentication == null) {
				throw new UnAuthorizedException();
			}
			
			// Assuming Principle is UUID , if principle changes, please change this code
			UUID id = UUID.fromString(authentication.getPrincipal().toString());
			
			User user = userService.getUser(id);
			
			return mapper.map(user, UserResponseDTO.class);

		}
		
		@PutMapping("/{id}")
		@PreAuthorize("#id.toString() == authentication.principal.toString()")
		public UserResponseDTO udpatedUser(@PathVariable UUID id, UserUpdateDTO updatedUser){
			
			User user = mapper.map(updatedUser, User.class);
			user = userService.updateUser(id, user);
			return mapper.map(user, UserResponseDTO.class);
			
		}

		@DeleteMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
			
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
