package com.adrasha.core.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.adrasha.core.model.Gender;
import com.adrasha.core.model.Name;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class MemberDataResponseDTO {
	
	private UUID id;
	private UUID familyId;
	private UUID ashaId;
	private Name name;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String birthPlace;
	private Integer age;
	private String adharNumber;
	private String abhaNumber;
	private String mobileNumber;
	private Boolean alive = true;
	
	private Instant createdAt;
	
	private Instant updatedAt;
}
