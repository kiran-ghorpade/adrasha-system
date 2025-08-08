package com.adrasha.core.dto.reports;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class HealthRecordReportDTO {

	private String memberName;
	
	private Instant recordedAt;
	
    private Boolean pregnant;
    	
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;
}
