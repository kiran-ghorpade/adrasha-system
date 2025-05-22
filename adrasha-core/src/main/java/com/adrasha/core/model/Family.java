package com.adrasha.core.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Family {
	
	private UUID id;
	
	private Member headMember;
	
	@NotEmpty
	private List<Member> members;
	
	@NotNull
	private Address address;
	
	@NotNull
	private UUID ashaId;
	
	private Instant createdAt;
	
	private Instant updatedAt;
	
	private String createdBy;
}
