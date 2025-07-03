package com.adrasha.analytics.dto;

import java.util.Map;

import com.adrasha.core.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class UserStats {
    private long totalUsers;
    private Map<Role, Long> roleDistribution;
}