package com.adrasha.user.model;
import java.util.UUID;

import com.adrasha.core.model.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends Auditable{

	@Id
	private UUID userId;
	
	private Name name;

	private UUID healthCenterId;
	
	private String adharNumber;
	
}
