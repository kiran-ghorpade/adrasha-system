package com.adrasha.core.model;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pregnancy {

	private UUID id;
	
	private UUID motherId;
	
	private UUID fatherId;
	
    private Instant conceptionDate;

    private Instant expectedDeliveryDate;

    private Boolean isHighRisk;
    
}
