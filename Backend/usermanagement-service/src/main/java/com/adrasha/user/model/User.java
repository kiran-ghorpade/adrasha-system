package com.adrasha.user.model;

import java.time.Instant;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	private UUID id;

	@Column(unique = true)
	private String mobileNumber;
	
	private Instant lastPasswordReset;

	private Instant createdAt;

	private Instant updatedAt;

}
