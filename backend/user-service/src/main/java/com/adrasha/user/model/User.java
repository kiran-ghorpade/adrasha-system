package com.adrasha.user.model;
import java.util.Set;
import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.Name;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;

	private UUID healthCenterId;
	
	private String adharNumber;
	
}
