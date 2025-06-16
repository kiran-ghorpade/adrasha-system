package com.adrasha.core.dto;

import java.util.List;
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
    private List<String> roles;
    private String status;
}
