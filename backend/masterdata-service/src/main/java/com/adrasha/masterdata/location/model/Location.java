package com.adrasha.masterdata.location.model;

import com.adrasha.masterdata.base.model.MasterEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Location extends MasterEntity{
	
	@Enumerated(EnumType.STRING)
	private LocalityType type;
	
	private String pincode;
	
	@ManyToOne
	@JoinColumn(name = "subdistrict_id")
	private Subdistrict subdistrict;
	
}