package com.adrasha.analytics.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics/reports")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Analytics Report")
@PreAuthorize("hasRole('ASHA')")
public class ReportsController {

	
	@PostMapping
	public void generateReports() {
		
	}
}
