package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.FamilyDataFilterDTO;
import com.adrasha.core.response.dto.FamilyDataResponseDTO;

@FeignClient(name = "data-service" , path = "/data/families")
public interface FamilyDataClient {
	
	   @GetMapping
	    Page<FamilyDataResponseDTO> getAll(FamilyDataFilterDTO filterDTO, Pageable pageable);

	    @GetMapping("/count")
	    Map<String, Long> getCount(FamilyDataFilterDTO filterDTO);
}
