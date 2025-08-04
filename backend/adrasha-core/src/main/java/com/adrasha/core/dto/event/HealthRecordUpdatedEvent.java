package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthRecordUpdatedEvent {
	private UUID ashaId;
    private UUID oldNCD;
    private UUID newNCD;
    private Instant createdAt;
}
