package com.adrasha.analytics.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.analytics.family.dto.FamilyStats;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Health Analytics")
@PreAuthorize("hasRole('ASHA')")
public class SummaryController {

    @GetMapping("/family/stats")
    public FamilyStats getFamilyStats() {
		return null;
    }
}
