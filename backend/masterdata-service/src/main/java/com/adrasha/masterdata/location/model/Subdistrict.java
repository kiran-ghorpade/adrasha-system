package com.adrasha.masterdata.location.model;

import com.adrasha.masterdata.base.model.MasterEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Subdistrict extends MasterEntity{

	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;
	
}
