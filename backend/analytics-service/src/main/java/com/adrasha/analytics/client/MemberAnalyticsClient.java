package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;

@FeignClient(name = "data-service",contextId = "MemberAnalyticsClient")
public interface MemberAnalyticsClient {

    @GetMapping("/api/v1/data/member/analytics/gender")
    Map<Gender, Long> getGenderCounts();

    @GetMapping("/api/v1/data/member/analytics/status")
    Map<AliveStatus, Long> getStatusCounts();

    @GetMapping("/api/v1/data/member/analytics/age-group")
    Map<String, Long> getAgeGroupCounts();

    @GetMapping("/api/v1/data/member/analytics/head-count")
    Long getHeadMemberCount();
}
