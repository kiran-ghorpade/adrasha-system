package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.NCDFilterDTO;
import com.adrasha.core.response.dto.NCDResponseDTO;

@FeignClient(name = "masterdata-service", contextId = "NCDClient", path = "/masterdata/ncd")
public interface NCDClient {
	
    @GetMapping
    Page<NCDResponseDTO> getAll(@SpringQueryMap NCDFilterDTO filterDTO,@SpringQueryMap Pageable pageable);
    
    @GetMapping("/count")
    Map<String, Long> getCount(@SpringQueryMap NCDFilterDTO filterDTO);

}
