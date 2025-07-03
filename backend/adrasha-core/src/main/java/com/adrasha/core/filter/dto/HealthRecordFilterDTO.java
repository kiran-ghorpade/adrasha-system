package com.adrasha.core.filter.dto;

import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class HealthRecordFilterDTO {

	private UUID memberId;
    
	private UUID ashaId;
	
    private Boolean pregnant;
    
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;
}
