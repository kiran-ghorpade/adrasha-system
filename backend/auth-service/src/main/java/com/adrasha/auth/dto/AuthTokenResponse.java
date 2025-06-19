package com.adrasha.auth.dto;

import java.time.Instant;

import com.adrasha.auth.dto.core.JwtUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponse{

	private JwtUser user;
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private Instant exp;

}
