package com.adrasha.family.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyRegistrationDTO {

	@NotNull
	private FamilyDTO family;
	
	@NotNull
	private HeadMemberDTO headMember;
	
}
