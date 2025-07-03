package com.adrasha.analytics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class ReportResponseDTO {
    private String filename;
    private String content;
}