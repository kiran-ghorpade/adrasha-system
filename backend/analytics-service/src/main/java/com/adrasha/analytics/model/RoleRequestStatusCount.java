package com.adrasha.analytics.model;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.RequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class RoleRequestStatusCount {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	private Long count;

	private Instant createdAt;
}
