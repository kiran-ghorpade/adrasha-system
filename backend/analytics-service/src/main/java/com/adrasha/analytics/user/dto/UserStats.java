package com.adrasha.analytics.user.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStats {
    private long totalUsers;
    private Map<String, Integer> roleDistribution;
}