package com.adrasha.family.dto.member;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.Gender;
import com.adrasha.family.model.Name;

import lombok.Data;

@Data
public class MemberResponseDTO {
	
	private UUID id;
	private UUID familyId;
	private Name name;
	private Gender gender;
	private Instant dateOfBirth;
	private String birthPlace;
	private String adharNumber;
	private String abhaNumber;
	private String mobileNumber;
	private boolean maritalStatus;
	private Instant marriageDate;
	private UUID spouseId;
	private UUID fatherId;
	private UUID motherId;
	private boolean alive = true;
}
