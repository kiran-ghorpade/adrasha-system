package com.adrasha.user.model;

import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.Name;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;

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
public class RoleRequest extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private Name name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private UUID userId;
	
	private UUID healthCenterId;
	
	@Enumerated(EnumType.STRING)
	private RequestStatus status;
	
	private String remark;
	
}
