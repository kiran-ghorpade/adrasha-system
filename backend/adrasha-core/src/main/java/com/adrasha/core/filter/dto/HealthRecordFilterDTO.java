package com.adrasha.core.filter.dto;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthRecordFilterDTO {

	private UUID memberId;
    
	private UUID ashaId;
	
    private Boolean pregnant;
    
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;
}
