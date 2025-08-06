package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.core.model.Name;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRequestDTO {
	private UUID id;
	private Name name;
	private Role role;
	private UUID userId;	
	private UUID healthCenterId;	
	private RequestStatus status;
	private String remark;
}
