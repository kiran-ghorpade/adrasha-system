package com.adrasha.ashaservice.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Asha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotNull
	private UUID userId;
	
	@NotNull
	@Embedded
	private Name name;

	@NotBlank
	@Column(unique = true)
	private String mobileNumber;

	@NotNull
	private UUID phcId;

//	@NotNull
//	private UUID address;

	private long totalFamilies;

	private long population;
	
	@CreationTimestamp
	private Instant createdAt;
	
	@UpdateTimestamp
	private Instant updatedAt;
	
//	@Version
//	private Integer version;

}
