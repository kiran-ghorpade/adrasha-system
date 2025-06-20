package com.adrasha.analytics.client;

import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrasha.core.response.dto.HealthCenterResponseDTO;

@FeignClient(name = "masterdata-service", path = "/masterdata/healthCenters")
public interface HealthCenterDataClient {
	
	@GetMapping("/{id}")
	HealthCenterResponseDTO get(UUID id);
	
    @GetMapping
    Page<HealthCenterResponseDTO> getAll(@RequestParam Map<String, Object> params, Pageable pageable);

}
