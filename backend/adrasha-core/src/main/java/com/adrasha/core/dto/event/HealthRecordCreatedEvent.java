package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthRecordCreatedEvent {
	private UUID ashaId;
    private UUID ncd;
    private Instant createdAt;
}
