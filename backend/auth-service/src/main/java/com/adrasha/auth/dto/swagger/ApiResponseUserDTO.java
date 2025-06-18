package com.adrasha.auth.dto.swagger;

import com.adrasha.auth.dto.UserDTO;
import com.adrasha.auth.dto.core.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class ApiResponseUserDTO extends Response<UserDTO>{}
