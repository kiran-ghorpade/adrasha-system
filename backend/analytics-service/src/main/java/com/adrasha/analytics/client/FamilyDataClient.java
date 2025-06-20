package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrasha.core.response.dto.FamilyResponseDTO;

@FeignClient(name = "data-service" , path = "/data/families")
public interface FamilyDataClient {
	
	   @GetMapping
	    Page<FamilyResponseDTO> getAll(@RequestParam Map<String, Object> params, Pageable pageable);

}
