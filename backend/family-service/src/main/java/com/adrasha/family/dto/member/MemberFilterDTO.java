package com.adrasha.family.dto.member;

import java.util.UUID;

import com.adrasha.core.model.Gender;

import lombok.Data;

@Data
public class MemberFilterDTO {
	private UUID familyId;
	private Gender gender;
	private String birthPlace;
	private boolean maritalStatus;
	private boolean alive = true;
}
