package com.adrasha.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/users")
public interface UserDataClient {

	@GetMapping("/me")
	ResponseEntity<Void> getCurrentUserDetails();
}
