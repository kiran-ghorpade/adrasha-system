package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrasha.core.response.dto.UserResponseDTO;

@FeignClient(name = "user-service", path = "/users")
public interface UserDataClient {

	@GetMapping("/me")
	UserResponseDTO getCurrentUserDetails();
	
    @GetMapping
    Page<UserResponseDTO> getAll(@RequestParam Map<String, Object> params, Pageable pageable);
    
    @GetMapping("/role-distribution")
    Map<String, Integer> getRoleDistribution();

}
