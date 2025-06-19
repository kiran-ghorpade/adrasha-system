package com.adrasha.data.health.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import lombok.Data;


@Data
public class HealthUpdateDTO {
	
	private Instant date;
	
    private Boolean pregnant;
	
	private Double height;
	
	private Double weight;
	
    @ElementCollection
	private Set<UUID> NCDList;

}
