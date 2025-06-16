package com.adrasha.core.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class HealthProfile {


    private UUID id;

    private Member member;

    private String bloodType;

    private Double heightCm;

    private Double weightKg;

    private String allergies;

    private String chronicConditions;

//    private List<Vaccination> vaccinationHistory;
    
    private LocalDate lastUpdated;
}
