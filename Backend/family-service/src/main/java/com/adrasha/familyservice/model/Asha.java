package com.adrasha.familyservice.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class Asha {
	
	private UUID id;

	@Embedded
	private Name name;

	private String mobileNumber;

	private UUID phcId;

	private UUID address;

	private long totalFamilies;

	private long population;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
