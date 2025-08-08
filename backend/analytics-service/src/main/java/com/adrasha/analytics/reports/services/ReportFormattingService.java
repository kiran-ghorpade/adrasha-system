package com.adrasha.analytics.reports.services;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.reports.dto.ReportData;
import com.adrasha.analytics.reports.utils.ReportGenerator;
import com.adrasha.analytics.reports.utils.ReportTableGenerator;

@Service
public class ReportFormattingService {

    public byte[] formatReport(ReportData reportData, String reportName) {
    	
        return ReportGenerator.createDocument()
                .addTitle(reportName)
//                .addHeader(request.getReportName()) // Add header here
                .addTable(ReportTableGenerator
                	    .create(reportData.getHeaders().size())
                	    .addHeaders(reportData.getHeaders())
                	    .addData(reportData.getRows())
                	    .build()) // Add the table with data
                .addFooter()
                .closeAndGetPdf(); // Add footer

    }
}