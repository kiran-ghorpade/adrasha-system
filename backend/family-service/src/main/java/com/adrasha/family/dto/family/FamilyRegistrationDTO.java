package com.adrasha.family.dto.family;

import com.adrasha.family.dto.member.HeadMemberDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyRegistrationDTO {

	@NotNull
	private FamilyCreateDTO family;
	
	@NotNull
	private HeadMemberDTO headMember;
	
}
