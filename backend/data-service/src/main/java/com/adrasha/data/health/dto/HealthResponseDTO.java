package com.adrasha.data.health.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import lombok.Data;


@Data
public class HealthResponseDTO {
	
	private UUID id;

	private UUID memberId;
    
	private UUID ashaId;
	
	private Instant date;
	
    private Boolean pregnant;
    	
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;

}
