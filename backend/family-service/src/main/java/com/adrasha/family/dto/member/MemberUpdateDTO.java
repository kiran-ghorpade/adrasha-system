package com.adrasha.family.dto.member;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.Gender;
import com.adrasha.family.model.Name;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdateDTO {

	@NotNull
	private UUID familyId;
	
	@Valid
	private Name name;
	
	@NotNull
	private Gender gender;

	@Past
	private Instant dateOfBirth;

	private String birthPlace;

	@NotBlank
	@Size(min = 10, max = 12)
	private String adharNumber;

	private String abhaNumber;

	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String mobileNumber;

	private boolean maritalStatus;
//
//	@PastOrPresent
//	private Instant marriageDate;
//
//	private UUID spouseId;
//
//	private UUID fatherId;
//
//	private UUID motherId;
//
//	@Default
//	private boolean alive = true;
}
