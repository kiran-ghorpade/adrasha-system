package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCreatedEvent {
	private UUID ashaId;
    private Gender gender;
    private AgeGroup ageGroup;
    private Instant createdAt;
}
