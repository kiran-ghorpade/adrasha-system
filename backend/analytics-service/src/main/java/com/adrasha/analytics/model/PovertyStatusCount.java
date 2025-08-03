package com.adrasha.analytics.model;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PovertyStatusCount {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID UUID;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PovertyStatus povertyStatus;

	@Column(nullable = false)
	private Long count;

	@Column(nullable = false)
	private Instant createdAt;
}
