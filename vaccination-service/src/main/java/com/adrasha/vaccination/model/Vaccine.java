package com.adrasha.vaccination.model;

import java.util.List;
import java.util.UUID;

import com.adrasha.core.convertor.UpperCaseConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class Vaccine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String name;
	
	@NotNull
	private Integer totaldosage;
	
	@OneToMany
	private List<DoseSchedule> schedule;
	
	@Convert(converter = UpperCaseConverter.class)
	private String description;
}
