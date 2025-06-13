package com.adrasha.user.dto;

import com.adrasha.user.model.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequestDTO {
	
	private String role;
	
	private RequestStatus status;

}
