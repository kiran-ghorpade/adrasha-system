package com.adrasha.authservice.dto;

import java.util.List;
import java.util.UUID;

import com.adrasha.authservice.model.AccountStatus;

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
    private List<String> roles;
    private AccountStatus status;
}