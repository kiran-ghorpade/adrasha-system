package com.adrasha.health.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class HealthInfo {

	@Id
	private UUID memberId;
		
	private Integer totalVaccinesTaken;
	
	private Double Height;
	
	private Double Weigth;
	
	private boolean haveNCD;
	
	private Set<String> NCDList;
}
