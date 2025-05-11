package com.adrasha.ncd.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DoseSchedule {

	private Vaccine id;
	
	@NotNull
	private Integer doseNumber;
	
	@NotNull
    private Integer minAgeDays;
	
	@NotNull
    private Integer maxAgeDays;

}
