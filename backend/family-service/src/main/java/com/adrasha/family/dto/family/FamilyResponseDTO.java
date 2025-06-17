package com.adrasha.family.dto.family;

import java.util.List;
import java.util.UUID;

import com.adrasha.family.model.Member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FamilyResponseDTO {
	private UUID id;

	private Member headMember;
	
	@NotEmpty
	private List<Member> members;
	
//	@NotNull
//	private UUID address;
	
	@NotNull
	private UUID ashaId;
}
