package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.HealthRecordFilterDTO;
import com.adrasha.core.response.dto.HealthRecordResponseDTO;

@FeignClient(name = "data-service" , path = "/data/healthRecords")
public interface HealthRecordClient {

	   @GetMapping
	    Page<HealthRecordResponseDTO> getAll(HealthRecordFilterDTO filterDTO, Pageable pageable);

	    @GetMapping("/count")
	    Map<String, Long> getCount(HealthRecordFilterDTO filterDTO);
}
