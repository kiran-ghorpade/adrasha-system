package com.adrasha.core.filter.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterDTO {
	
	private UUID healthCenterId;
	
//	private Set<String> roles;

	
}
