package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.LocationFilterDTO;
import com.adrasha.core.response.dto.LocationResponseDTO;

@FeignClient(name = "masterdata-service", contextId = "LocationClient", path = "/masterdata/locations")
public interface LocationClient {
	
    @GetMapping
    Page<LocationResponseDTO> getAll(@SpringQueryMap LocationFilterDTO filterDTO,@SpringQueryMap Pageable pageable);
    
    @GetMapping("/count")
    Map<String, Long> getCount(@SpringQueryMap LocationFilterDTO filterDTO);

}
