package com.adrasha.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adrasha.user.service.serviceImpl.UserServiceImpl;

@SpringBootTest
class AdrashaUserServiceApplicationTests {	
	
	@Autowired
	UserServiceImpl userService;

	@Test
	void contextLoads() {
	}
}
