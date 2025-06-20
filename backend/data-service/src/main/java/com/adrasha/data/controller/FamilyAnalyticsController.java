package com.adrasha.data.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/data/families")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Family Management")
@PreAuthorize("hasRole('ASHA')")
public class FamilyAnalyticsController {

	
}
