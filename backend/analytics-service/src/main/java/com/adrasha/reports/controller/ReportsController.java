package com.adrasha.reports.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.reports.services.ReportService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reports")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses({
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
@Tag(name = "Reports")
@PreAuthorize("hasRole('ASHA')")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/family")
    public ResponseEntity<byte[]> generateFamilyReport(@RequestParam UUID ashaId) {
        byte[] response = reportService.generateFamilyReport(ashaId);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=family-report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(response);
    }
    
    @GetMapping("/member")
    public ResponseEntity<byte[]> generateMemberReport(@RequestParam UUID ashaId) {
        byte[] response = reportService.generateMemberReport(ashaId);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=member-report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<byte[]> generateHealthRecordsReport(@RequestParam UUID ashaId) {
        byte[] response = reportService.generateHealthReport(ashaId);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=health-report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(response);
    }
}
