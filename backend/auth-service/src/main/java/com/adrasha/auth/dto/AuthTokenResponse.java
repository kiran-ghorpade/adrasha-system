package com.adrasha.auth.dto;

import java.time.Instant;

import com.adrasha.auth.dto.core.JwtUser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class AuthTokenResponse{

	private JwtUser user;
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private Instant exp;

}
