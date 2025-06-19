package com.adrasha.data.family.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Schema
public class FamilyRegistrationDTO {

	@Valid
	private FamilyCreateDTO family;

	@Valid
	private HeadMemberDTO headMember;
	
}
