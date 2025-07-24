package com.adrasha.core.dto.filter;

import java.time.LocalDate;
import java.util.UUID;

import com.adrasha.core.model.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@Schema
public class MemberDataFilterDTO {
	private UUID familyId;
	private UUID ashaId;
	private Gender gender;
	private LocalDate dateOfBirth;
	private Integer age;
	
	@Default
	private Boolean alive = true;
}
