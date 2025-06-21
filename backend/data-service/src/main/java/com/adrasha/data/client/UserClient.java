package com.adrasha.data.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", contextId = "UserClient", path = "/users")
public interface UserClient {

	@GetMapping("/{id}")
	ResponseEntity<?> getUser(@RequestParam UUID id);
}
