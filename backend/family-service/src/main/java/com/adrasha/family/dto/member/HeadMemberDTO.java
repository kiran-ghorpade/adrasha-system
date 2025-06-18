package com.adrasha.family.dto.member;

import java.time.Instant;

import com.adrasha.core.model.Gender;
import com.adrasha.family.model.Name;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class HeadMemberDTO {

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

	private String mobileNumber;

	@Default
	private boolean alive = true;
}
