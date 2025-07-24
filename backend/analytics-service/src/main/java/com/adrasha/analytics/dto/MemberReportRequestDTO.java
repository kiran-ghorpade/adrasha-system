package com.adrasha.analytics.dto;

import java.util.List;

import lombok.Data;

@Data
public class MemberReportRequestDTO {

    private List<String> fields;         // fields to include in report
    private String memberGender;         // e.g., "MALE", "FEMALE"
    private Integer memberAgeMin;
    private Integer memberAgeMax;
    private String memberStatus;         // e.g., "ALIVE", "DECEASED"

    // Other filters and report-specific parameters

    // Getters and setters
}
