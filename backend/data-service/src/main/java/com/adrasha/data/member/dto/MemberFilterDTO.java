package com.adrasha.data.member.dto;

import java.util.UUID;

import com.adrasha.core.model.Gender;

import lombok.Data;

@Data
public class MemberFilterDTO {
	private UUID familyId;
	private Gender gender;
	private Boolean maritalStatus;
	private Boolean alive = true;
}
