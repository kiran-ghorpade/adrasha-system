package com.adrasha.core.dto.event;

import java.time.Instant;

import com.adrasha.core.model.RequestStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRequestDeletedEvent {
	private RequestStatus status;
	private Instant createdAt;
}
