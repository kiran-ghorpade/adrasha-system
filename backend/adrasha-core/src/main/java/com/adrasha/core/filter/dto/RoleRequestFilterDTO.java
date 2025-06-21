package com.adrasha.core.filter.dto;

import java.util.UUID;

import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;

import lombok.Data;

@Data
public class RoleRequestFilterDTO {
	
	private Role role;
	
	private RequestStatus status;
	
	private UUID healthCenter;
}
