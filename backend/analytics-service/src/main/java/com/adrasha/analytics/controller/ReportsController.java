package com.adrasha.analytics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Report Generation")
public class ReportsController {

//    @Autowired
//    private ReportService reportService;
//
//    @PostMapping("/admin/reports/generate")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<Resource> generateAdminReport(@RequestBody ReportRequestDTO request) {
//    }
	
//    
//    @PostMapping("/asha/reports/generate")
//    @PreAuthorize("hasAnyRole('ASHA')")
//    public ResponseEntity<Resource> generateASHAReport(@RequestBody ReportRequestDTO request) {

//    }
}