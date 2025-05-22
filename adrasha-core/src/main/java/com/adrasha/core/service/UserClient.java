package com.adrasha.core.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrasha.core.model.User;

@FeignClient("AUTH-SERVICE")
public interface UserClient{
	
	    @GetMapping("/api/v1/users")
	    ResponseEntity<User> loadUserByUsername(@RequestParam String username);

}
