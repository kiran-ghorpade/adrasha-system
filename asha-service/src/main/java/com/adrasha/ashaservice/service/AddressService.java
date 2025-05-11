package com.adrasha.ashaservice.service;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.ashaservice.model.Address;

@FeignClient("ADDRESS-SERVICE")
public interface AddressService {

	@GetMapping("/api/address/{id}")
	ResponseEntity<Address> getAddress(UUID id);
}
