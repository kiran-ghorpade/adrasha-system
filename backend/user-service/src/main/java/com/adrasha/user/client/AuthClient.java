package com.adrasha.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.adrasha.core.config.FeignConfiguration;
import com.adrasha.core.dto.JwtUser;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;

@FeignClient(name = "auth-service", path = "/auth/roles", configuration = FeignConfiguration.class)
public interface AuthClient {

	@PutMapping
	public JwtUser addRole(@RequestBody RoleUpdateDTO addRoleDTO);
	
	@DeleteMapping
	public JwtUser removeRole(@RequestBody RoleUpdateDTO addRoleDTO);
	
}
