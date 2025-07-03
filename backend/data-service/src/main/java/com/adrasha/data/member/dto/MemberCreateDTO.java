package com.adrasha.data.member.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.adrasha.core.model.Gender;
import com.adrasha.core.model.Name;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class MemberCreateDTO {
	
	@NotNull
	private UUID familyId;
	
	@NotNull
	private UUID ashaId;
	
	@Valid
	private Name name;
	
	@NotNull
	private Gender gender;

	@Past
	private LocalDate dateOfBirth;

	private String birthPlace;

	@NotBlank
	@Size(min = 10, max = 12)
	private String adharNumber;

	private String abhaNumber;

	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String mobileNumber;

}
