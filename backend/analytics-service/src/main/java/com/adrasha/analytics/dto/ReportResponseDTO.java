package com.adrasha.analytics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportResponseDTO {
    private String filename;
    private String content;
}