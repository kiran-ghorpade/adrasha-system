package com.adrasha.monolithic.dto;

import com.adrasha.monolithic.model.Family;
import com.adrasha.monolithic.model.Member;

import lombok.Data;

@Data
public class FamilyRegistrationDTO {

	private Family family;
	private Member member;
}
