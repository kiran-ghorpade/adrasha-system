package com.adrasha.familyservice.dto;

import java.util.List;
import java.util.UUID;

import com.adrasha.familyservice.model.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FamilyDTO {

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Member headMember;
	
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	private List<Member> members;
	
//	@NotNull
//	private UUID address;
	
	@NotNull
	private UUID ashaId;
}
