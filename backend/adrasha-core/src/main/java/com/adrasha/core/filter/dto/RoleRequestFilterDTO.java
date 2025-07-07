package com.adrasha.core.filter.dto;

import java.util.UUID;

import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class RoleRequestFilterDTO {
	
	private Role role;
	
	private RequestStatus status;
	
	private UUID healthCenterId;
}
