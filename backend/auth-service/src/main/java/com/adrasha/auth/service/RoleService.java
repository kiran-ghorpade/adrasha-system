package com.adrasha.auth.service;

import java.util.UUID;

import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.model.Role;

public interface RoleService {

	UserDTO addRole(UUID userId, Role role);

	UserDTO removeRole(UUID userId, Role role);

}
