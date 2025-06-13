package com.adrasha.masterdata.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Locality {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private LocalityType type;
	
	@Pattern(regexp = "[0-9]+")
	@Size(min = 6, max = 6)
	private String pincode;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "subdistrict_id")
	private Subdistrict subdistrict;
	
}
