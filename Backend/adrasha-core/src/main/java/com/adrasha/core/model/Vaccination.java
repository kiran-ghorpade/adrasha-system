package com.adrasha.core.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class Vaccination {

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
