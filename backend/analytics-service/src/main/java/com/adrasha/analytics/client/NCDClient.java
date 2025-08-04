package com.adrasha.analytics.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.adrasha.core.dto.response.NCDResponseDTO;

@FeignClient(name = "masterdata-service", contextId = "NCDClient")
public interface NCDClient {
	
	@GetMapping("/masterdata/ncd/{id}")
	NCDResponseDTO getNCDDetails(@PathVariable UUID id);	
}
