package com.adrasha.ncd.model;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NCDSurvey {

	private UUID id;
	
	@NotNull
	private Integer doseNumber;
	
	@NotNull
    private Integer minAgeDays;
	
	@NotNull
    private Integer maxAgeDays;

}
