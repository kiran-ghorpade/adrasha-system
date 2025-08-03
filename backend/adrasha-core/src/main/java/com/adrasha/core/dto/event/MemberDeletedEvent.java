package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class MemberDeletedEvent {
	private UUID ashaId;
    
    private Instant createdAt;
}
