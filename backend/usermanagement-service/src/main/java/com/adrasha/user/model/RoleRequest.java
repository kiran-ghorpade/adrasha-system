package com.adrasha.user.model;

import java.util.UUID;

import com.adrasha.core.model.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class RoleRequest extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Valid
	private Name name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private UUID userId;
	
	@Enumerated(EnumType.STRING)
	private RequestStatus status;
	
	private UUID healthCenter;

}
