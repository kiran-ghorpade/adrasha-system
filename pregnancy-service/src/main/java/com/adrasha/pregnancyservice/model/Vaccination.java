package com.adrasha.pregnancyservice.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class Vaccination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	@NotNull
	private UUID memberId;
	
	@NotNull
	private Vaccine vaccine;
	
	@NotNull
	private Integer doseNumber;
	
	@NotNull
	private Instant vaccinationDate;
	
    private String healthcareProvider;

    private String location;

}
