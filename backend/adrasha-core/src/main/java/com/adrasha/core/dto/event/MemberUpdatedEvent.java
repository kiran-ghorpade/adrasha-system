package com.adrasha.core.dto.event;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdatedEvent {
	private UUID ashaId;
    private UUID memberId;
    private UUID familyId;
    private Gender gender;
    private AgeGroup ageGroup;
    private AliveStatus alive;
    
    private Instant createdAt;
}
