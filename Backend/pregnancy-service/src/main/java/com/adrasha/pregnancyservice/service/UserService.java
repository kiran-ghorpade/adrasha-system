package com.adrasha.pregnancyservice.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("AUTH-SERVICE")
public interface UserService {
	
}
