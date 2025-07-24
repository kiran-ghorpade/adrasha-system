package com.adrasha.core.dto.filter;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class UserFilterDTO {
	
	private UUID healthCenterId;
	
//	private Set<String> roles;

	
}
