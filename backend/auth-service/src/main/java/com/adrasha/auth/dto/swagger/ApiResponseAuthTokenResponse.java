package com.adrasha.auth.dto.swagger;

import com.adrasha.auth.dto.AuthTokenResponse;
import com.adrasha.auth.dto.core.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class ApiResponseAuthTokenResponse extends Response<AuthTokenResponse> {}
