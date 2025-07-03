package com.adrasha.core.response.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema
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
