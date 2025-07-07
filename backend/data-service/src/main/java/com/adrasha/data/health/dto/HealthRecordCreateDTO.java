package com.adrasha.data.health.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;


@Data
@Schema
public class HealthRecordCreateDTO {

	@NotNull
	private UUID memberId;
    
	@NotNull
	private UUID ashaId;
	
	@PastOrPresent
	private Instant date;
	
    private Boolean pregnant;
	
	private Double height;
	
	private Double weight;
	
	private Set<UUID> NCDList;

}
