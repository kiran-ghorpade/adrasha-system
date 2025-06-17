package com.adrasha.user.model;

import java.util.UUID;

import com.adrasha.core.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
public class RoleRequest extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Valid
	private Name name;
	
	private String role;
	
	@Column(unique = true)
	private UUID userId;
	
	private RequestStatus status;
	
	private UUID healthCenter;

}
