package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.UserFilterDTO;
import com.adrasha.core.model.Role;
import com.adrasha.core.response.dto.UserResponseDTO;

@FeignClient(name = "user-service", path = "/users")
public interface UserDataClient {

	@GetMapping("/me")
	UserResponseDTO getCurrentUserDetails();
	
    @GetMapping
    Page<UserResponseDTO> getAll(UserFilterDTO filterDTO, Pageable pageable);
    
    @GetMapping("/role-distribution")
    Map<Role, Long> getRoleDistribution();
    
    @GetMapping("/count")
    Map<String, Long> getCount(UserFilterDTO filterDTO);

}
