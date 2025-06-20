package com.adrasha.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.user.service.UserService;

public class UserAnalyticsController {
	@Autowired
	private UserService userService;

	@GetMapping("/role-distribution")
	public Map<String, Integer> getRoleDistribution() {
		return userService.getRoleDistribution();
	}

}
