package com.adrasha.pregnancyservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.model.User;

@FeignClient("AUTH-SERVICE")
public interface UserService {
	
	@GetMapping("/api/v1/users?username={username}")
	public ResponseEntity<User> getUserByUsername(String username);

}
