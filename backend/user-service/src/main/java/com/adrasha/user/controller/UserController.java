package com.adrasha.user.controller;

import java.util.Map;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.filter.dto.UserFilterDTO;
import com.adrasha.core.model.Role;
import com.adrasha.core.response.dto.UserResponseDTO;
import com.adrasha.user.dto.user.UserUpdateDTO;
import com.adrasha.user.model.RoleRequest;
import com.adrasha.user.model.User;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
		
		@GetMapping("/count")
		public Map<String, Long> getTotalUsers(UserFilterDTO filterDTO) {
			User filter = mapper.map(filterDTO, User.class);

			Example<User> example = Example.of(filter, ExampleMatcherUtils.getDefaultMatcher());

			long total = userService.getTotalUserCount(example);
			return Map.of("count", total);
		}
		

		@GetMapping("/role-distribution")
		public Map<Role, Long> getRoleDistribution() {
			return userService.getRoleDistribution();
		}

		
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('USER')")
		public UserResponseDTO getUser(@PathVariable UUID id){
			
			User user = userService.getUser(id);
			
			return mapper.map(user, UserResponseDTO.class);
		}
	
		@PutMapping("/{id}")
		@PreAuthorize("#id.toString() == authentication.principal.toString()")
		public UserResponseDTO udpatedUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO updatedUser){
			
			User user = mapper.map(updatedUser, User.class);
			user = userService.updateUser(id, user);
			return mapper.map(user, UserResponseDTO.class);
			
		}
		
	    @PostMapping("/{id}/roles")
	    public ResponseEntity<?> addRole(@PathVariable UUID id, @RequestBody RoleRequest request) {
	        userService.addRoleToUser(id, request.getRole());
	        return ResponseEntity.created(null).build();
	    }

	    @DeleteMapping("/{id}/roles/{role}")
	    public ResponseEntity<?> removeRole(@PathVariable UUID id, @PathVariable String role) {
	        userService.removeRoleFromUser(id, role);
	        return ResponseEntity.noContent().build();
	    }

		@DeleteMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
			
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
