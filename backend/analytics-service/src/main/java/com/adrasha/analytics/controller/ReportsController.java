package com.adrasha.analytics.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@Tag(name = "Report Generation")
public class ReportsController {

//    @Autowired
//    private ReportService reportService;
//
//    @PostMapping("/admin/reports/generate")
//	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<Resource> generateAdminReport(@RequestBody ReportRequestDTO request) {
//    }
	
//    
//    @PostMapping("/asha/reports/generate")
//	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FamilyPageResponseDTO.class)))
	//    @PreAuthorize("hasAnyRole('ASHA')")
//    public ResponseEntity<Resource> generateASHAReport(@RequestBody ReportRequestDTO request) {

//    }
}