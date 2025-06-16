package com.adrasha.health.dto;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class HealthResponseDTO {
	
	@NotNull
	private UUID memberId;
	
	private Integer totalVaccinesTaken;
	
	private Double Height;
	
	private Double Weigth;
	
	private boolean haveNCD;

}
