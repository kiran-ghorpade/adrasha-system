package com.adrasha.monolithic.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class HealthProfile {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    private String bloodType;

    private Double heightCm;

    private Double weightKg;

    private String allergies;

    private String chronicConditions;

    @OneToMany(mappedBy = "healthProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccination> vaccinationHistory;
    
    private LocalDate lastUpdated;
}
