package com.adrasha.ashaservice.dto;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.ashaservice.model.Address;
import com.adrasha.ashaservice.model.Name;
import com.adrasha.ashaservice.model.PrimaryHealthCenter;

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
	private String mobileNumber;
	private PrimaryHealthCenter phc;
	private Address address;
	private long totalFamilies;
	private long population;
	private Instant createdAt;
	private Instant updatedAt;
}
