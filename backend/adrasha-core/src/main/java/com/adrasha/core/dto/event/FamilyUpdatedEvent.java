package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.PovertyStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyUpdatedEvent {
	private UUID ashaId;
    private PovertyStatus oldPovertyStatus;
    private PovertyStatus newPovertyStatus;
    private Instant createdAt;
}
