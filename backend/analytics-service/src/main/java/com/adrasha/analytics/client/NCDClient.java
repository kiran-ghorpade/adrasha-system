package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.NCDFilterDTO;
import com.adrasha.core.response.dto.NCDResponseDTO;

@FeignClient(name = "masterdata-service", path = "/ncd")
public interface NCDClient {
	
    @GetMapping
    Page<NCDResponseDTO> getAll(NCDFilterDTO filterDTO, Pageable pageable);
    
    @GetMapping("/count")
    Map<String, Long> getCount(NCDFilterDTO filterDTO);

}
