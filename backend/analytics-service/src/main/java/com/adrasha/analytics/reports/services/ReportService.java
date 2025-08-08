package com.adrasha.analytics.reports.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.reports.dto.ReportData;
import com.adrasha.analytics.reports.integration.ReportDataService;

@Service
public class ReportService {

	@Autowired
	ReportDataService dataService;

	@Autowired
	ReportFormattingService formattingService;

	public byte[] generateFamilyReport(UUID ashaId) {
		ReportData reportData = dataService.fetchFamilyData(ashaId);
		return formattingService.formatReport(reportData, "Family Report");
	}

	public byte[] generateMemberReport(UUID ashaId) {
		ReportData reportData = dataService.fetchMemberData(ashaId);
		return formattingService.formatReport(reportData, "Member Report");
	}

	public byte[] generateHealthReport(UUID ashaId) {
		ReportData reportData = dataService.fetchHealthData(ashaId);
		return formattingService.formatReport(reportData, "Health Records Report");
	}

}
