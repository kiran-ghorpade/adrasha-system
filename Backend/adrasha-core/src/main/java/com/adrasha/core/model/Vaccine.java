package com.adrasha.core.model;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class Vaccine {

	private UUID id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Integer totaldosage;
	
	private List<DoseSchedule> schedule;
	
	private String description;
}
