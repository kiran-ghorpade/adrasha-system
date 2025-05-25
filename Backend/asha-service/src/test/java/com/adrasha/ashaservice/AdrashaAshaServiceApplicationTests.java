package com.adrasha.ashaservice;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adrasha.ashaservice.exception.AshaNotFoundException;
import com.adrasha.ashaservice.service.serviceImpl.AshaServiceImpl;

@SpringBootTest
class AdrashaAshaServiceApplicationTests {	
	
	@Autowired
	AshaServiceImpl ashaService;

	@Test
	void contextLoads() {
	}
	
//	@Test
	void testGetAllAsha() {
		Assertions.assertIterableEquals(ashaService.getAllAsha(null).getContent(), null);
	}
	
//	@Test
	void testGetAsha() {
		UUID ashaId = UUID.randomUUID();
		
		Assertions.assertThrows(AshaNotFoundException.class, 
				()-> ashaService.getAsha(ashaId));
	}
	 
}
