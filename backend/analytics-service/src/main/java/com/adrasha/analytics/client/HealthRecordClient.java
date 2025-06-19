package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "data-service" , path = "/data/healthRecords")
public interface HealthRecordClient {

	   @GetMapping
	    Page<?> getAll(@RequestParam Map<String, Object> params, Pageable pageable);

}
