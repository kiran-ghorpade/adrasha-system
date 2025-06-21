package com.adrasha.masterdata.model;

import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.HealthCenterType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class HealthCenter extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(unique = true)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private HealthCenterType centerType;
	
	private UUID locationId;
	
	private long totalFamilies;
	
	private long totalPopulation;
}