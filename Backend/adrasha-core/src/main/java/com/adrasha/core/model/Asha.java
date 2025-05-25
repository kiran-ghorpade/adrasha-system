package com.adrasha.core.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class Asha {
	
	private UUID id;

	private Name name;

	private String mobileNumber;

	private UUID phcId;

	private UUID address;

	private long totalFamilies;

	private long population;
	
	private Instant createdAt;
	
	private Instant updatedAt;

}
