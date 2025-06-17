package com.adrasha.user.dto.roleRequest;

import java.util.UUID;

import com.adrasha.user.model.RequestStatus;
import com.adrasha.user.model.Role;

import lombok.Data;

@Data
public class RoleRequestFilterDTO {
	
	private Role role;
	
	private RequestStatus status;
	
	private UUID healthCenter;
}
