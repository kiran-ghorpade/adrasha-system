package com.adrasha.user.dto.user;

import java.util.UUID;

import com.adrasha.user.model.Name;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
	
	private UUID id;
	
	private Name name;

	private UUID healthCenterId;
	
	private String adharNumber;
}
