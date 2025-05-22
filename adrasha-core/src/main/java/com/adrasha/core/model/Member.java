package com.adrasha.core.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	private UUID id;

	@NotNull
	private Name name;

	@NotNull
	private String gender;

	@Past
	private Instant dateOfBirth;

	private String birthPlace;

	@Size(min = 10, max = 12)
	private String adharNumber;

	private String abhaNumber;

	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String mobileNumber;

	private boolean maritalStatus;

	@PastOrPresent
	private Instant marriageDate;

	private Member spouse;

	private Member father;

	private Member mother;

	@NotNull
	@Default
	private boolean alive = true;

	@NotNull
	private Family family;
}
