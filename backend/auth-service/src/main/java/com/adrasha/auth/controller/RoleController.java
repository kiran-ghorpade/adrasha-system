package com.adrasha.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.auth.dto.RoleUpdateDTO;
import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.ErrorResponse;
import com.adrasha.auth.dto.core.ValidationErrorResponse;
import com.adrasha.auth.service.RoleService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Role Management (SYSTEM)")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasRole('SYSTEM')")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PutMapping("/roles")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public UserDTO addRole(@RequestBody RoleUpdateDTO addRoleDTO) {
				
		return roleService.addRole(addRoleDTO.getUserId(), addRoleDTO.getRole());
	}

	@DeleteMapping("/roles")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public UserDTO removeRole(@RequestBody RoleUpdateDTO addRoleDTO) {
				
		return roleService.removeRole(addRoleDTO.getUserId(), addRoleDTO.getRole());
	}
}
