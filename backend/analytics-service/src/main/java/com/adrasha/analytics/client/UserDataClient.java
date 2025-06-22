package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.UserFilterDTO;
import com.adrasha.core.response.dto.UserResponseDTO;

@FeignClient(name = "user-service", contextId = "UserDataClient", path = "/users")
public interface UserDataClient {

	@GetMapping("/me")
	UserResponseDTO getCurrentUserDetails();
	
    @GetMapping
    Page<UserResponseDTO> getAll(@SpringQueryMap UserFilterDTO filterDTO,@SpringQueryMap Pageable pageable);
   
    @GetMapping("/count")
    Map<String, Long> getCount(@SpringQueryMap UserFilterDTO filterDTO);

}
