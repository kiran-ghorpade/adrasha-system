package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import lombok.Data;


@Data
public class HealthRecordResponseDTO {
	
	private UUID id;

	private UUID memberId;
    
	private UUID ashaId;
	
	private Instant date;
	
    private Boolean pregnant;
    	
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
