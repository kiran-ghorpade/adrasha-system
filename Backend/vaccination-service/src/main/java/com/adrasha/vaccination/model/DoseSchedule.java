package com.adrasha.vaccination.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Embeddable
public class DoseSchedule {

	private Vaccine id;
	
	@NotNull
	private Integer doseNumber;
	
	@NotNull
    private Integer minAgeDays;
	
	@NotNull
    private Integer maxAgeDays;

}
