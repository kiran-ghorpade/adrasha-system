package com.adrasha.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.adrasha.core.config.FeignConfiguration;
import com.adrasha.user.dto.roleRequest.RoleUpdateDTO;

@FeignClient(name = "AUTH-SERVICE", url = "http://localhost:8081/auth", configuration = FeignConfiguration.class)
public interface AuthService {

	@PutMapping("/updateRole")
	public ResponseEntity<?> updateRole(@RequestBody RoleUpdateDTO addRoleDTO);
}
