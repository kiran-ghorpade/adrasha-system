package com.adrasha.authservice.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private Instant exp;

}
