package com.adrasha.apigateway.dto;

import java.util.Set;
import java.util.UUID;

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
    private Set<String> roles;
    private String status;
}