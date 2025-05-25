package com.adrasha.monolithic.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Asha {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Name name;

	private String mobileNumber;

	private UUID phcId;

	private UUID address;

	private long totalFamilies;

	private long population;
	
	@CreationTimestamp
	private Instant createdAt;
	
	@UpdateTimestamp
	private Instant updatedAt;

}
