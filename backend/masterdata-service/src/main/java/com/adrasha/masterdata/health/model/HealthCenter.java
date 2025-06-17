package com.adrasha.masterdata.health.model;

import java.util.UUID;

import com.adrasha.masterdata.base.model.MasterEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class HealthCenter extends MasterEntity {
	
	@Enumerated(EnumType.STRING)
	private HealthCenterType centerType;
	
	private UUID location;
}