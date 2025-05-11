package com.adrasha.familyservice.dto;

import com.adrasha.familyservice.model.Family;
import com.adrasha.familyservice.model.Member;

import lombok.Data;

@Data
public class FamilyRegistrationDTO {

	private Family family;
	private Member member;
}
