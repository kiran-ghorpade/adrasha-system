package com.adrasha.analytics.client;

import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.adrasha.core.filter.dto.HealthCenterFilterDTO;
import com.adrasha.core.response.dto.HealthCenterResponseDTO;

@FeignClient(name = "masterdata-service", contextId = "HealthCenterDataClient", path = "/masterdata/healthCenters")
public interface HealthCenterDataClient {
	
	@GetMapping("/{id}")
	HealthCenterResponseDTO get(@PathVariable UUID id);
	
    @GetMapping
    Page<HealthCenterResponseDTO> getAll(@SpringQueryMap HealthCenterFilterDTO filterDTO,@SpringQueryMap Pageable pageable);
    
    @GetMapping("/count")
    Map<String, Long> getCount(@SpringQueryMap HealthCenterFilterDTO filterDTO);

}
