package com.adrasha.ncd.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.model.User;

@Service
@FeignClient("AUTH-SERVICE")
public interface UserService {
	
	@GetMapping("/api/v1/users?username={username}")
	public ResponseEntity<User> getUserByUsername(String username);

}
