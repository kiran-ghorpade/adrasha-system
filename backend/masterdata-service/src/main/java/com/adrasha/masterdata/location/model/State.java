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
public class State extends MasterEntity{
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
}
