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

	private AliveStatus oldAliveStatus;
	private AliveStatus newAliveStatus;

	private AgeGroup oldAgeGroup;
	private AgeGroup newAgeGroup;

	private Gender oldGender;
	private Gender newGender;

	private Instant createdAt;
}
