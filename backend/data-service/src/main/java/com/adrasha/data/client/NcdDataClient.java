package com.adrasha.data.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adrasha.core.dto.response.NCDResponseDTO;

@FeignClient(name = "masterdata-service", contextId = "NcdDataClient")
public interface NcdDataClient {

	@GetMapping("/masterdata/ncd/{id}")
	ResponseEntity<NCDResponseDTO> getNcd(@RequestParam UUID id);

	@GetMapping("/masterdata/ncd")
	List<NCDResponseDTO> getAllNcd();
	
}
