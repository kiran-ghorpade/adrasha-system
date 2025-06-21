package com.adrasha.analytics.user.dto;

import java.util.Map;

import com.adrasha.core.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStats {
    private long totalUsers;
    private Map<Role, Long> roleDistribution;
}