package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyDeletedEvent {
	private UUID ashaId;
    private PovertyStatus povertyStatus;
    private Instant createdAt;
}
