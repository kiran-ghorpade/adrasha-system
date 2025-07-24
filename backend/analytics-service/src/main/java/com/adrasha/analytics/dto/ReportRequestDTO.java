package com.adrasha.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Schema
public class ReportRequestDTO {
    private String reportName;
    private List<String> fields;
    private Map<String, String> filters;
}