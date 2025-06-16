package com.adrasha.user.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.user.model.Name;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AshaDetailsDTO {
	
	
	private UUID id;
	private Name name;
	private UUID userId;
	private String mobileNumber;
//	private PrimaryHealthCenter phc;
//	private Address address;
	private long totalFamilies;
	private long population;
	private Instant createdAt;
	private Instant updatedAt;
}
