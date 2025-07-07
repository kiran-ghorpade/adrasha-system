package com.adrasha.data.model;

import java.util.UUID;

import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.PovertyStatus;

import jakarta.persistence.Column;
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
public class Family extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private UUID headMemberId;
	
	private UUID ashaId;
	
	@Enumerated(EnumType.STRING)
	private PovertyStatus povertyStatus;
	
}
