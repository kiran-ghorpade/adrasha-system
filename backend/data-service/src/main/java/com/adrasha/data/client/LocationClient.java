package com.adrasha.data.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "masterdata-service" , path = "/masterdata/locations")
public interface LocationClient {

	@GetMapping("/{id}")
	ResponseEntity<?> getLocation(@PathVariable UUID id);
	
}
