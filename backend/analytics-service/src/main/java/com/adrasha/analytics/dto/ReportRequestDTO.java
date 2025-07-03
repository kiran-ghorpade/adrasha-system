package com.adrasha.analytics.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema
public class ReportRequestDTO {
    private String entityType;
    private String subEntityType;
    private List<String> fields;
    private Map<String, String> filters;
}