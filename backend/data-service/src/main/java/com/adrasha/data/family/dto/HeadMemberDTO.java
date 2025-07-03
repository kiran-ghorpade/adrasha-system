package com.adrasha.data.family.dto;

import java.time.LocalDate;

import com.adrasha.core.model.Gender;
import com.adrasha.core.model.Name;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class HeadMemberDTO {

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
