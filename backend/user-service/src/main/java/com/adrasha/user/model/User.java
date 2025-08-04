package com.adrasha.user.model;
import java.util.Set;
import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.Name;
import com.adrasha.core.model.Role;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends Auditable{

	@Id
	private UUID id;
	
	private Name name;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	private UUID healthCenterId;
	
	private String adharNumber;
	
}
