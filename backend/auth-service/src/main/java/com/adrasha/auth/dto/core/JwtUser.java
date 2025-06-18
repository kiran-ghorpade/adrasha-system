package com.adrasha.auth.dto.core;

import java.util.Set;
import java.util.UUID;

import com.adrasha.auth.model.AccountStatus;
import com.adrasha.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtUser {
    private UUID id;
    private String username;
    private Set<Role> roles;
    private AccountStatus status;
}