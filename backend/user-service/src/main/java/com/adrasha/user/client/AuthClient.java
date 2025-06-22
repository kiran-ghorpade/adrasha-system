package com.adrasha.user.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.adrasha.core.dto.JwtUser;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;

@FeignClient(name = "auth-service", contextId = "AuthDataClient", path = "/auth/roles")
public interface AuthClient {

	@PutMapping
	public JwtUser addRole(@RequestBody RoleUpdateDTO addRoleDTO);
	
	@DeleteMapping
	public JwtUser removeRole(@RequestBody RoleUpdateDTO addRoleDTO);
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id);

	
}
