package com.adrasha.analytics.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adrasha.analytics.client.FamilyAnalyticsClient;
import com.adrasha.analytics.client.HealthAnalyticsClient;
import com.adrasha.analytics.client.MemberAnalyticsClient;

@Service
public class AnalyticsAggregatorService {
    private final MemberAnalyticsClient memberClient;
    private final FamilyAnalyticsClient familyClient;
    private final HealthAnalyticsClient healthClient;

    public AnalyticsAggregatorService(MemberAnalyticsClient memberClient,
                                     FamilyAnalyticsClient familyClient,
                                     HealthAnalyticsClient healthClient) {
        this.memberClient = memberClient;
        this.familyClient = familyClient;
        this.healthClient = healthClient;
    }

    public Map<String, Object> getFullAnalyticsOverview() {
        Map<String, Object> result = new HashMap<>();
        result.put("member", getMemberOverview());
        result.put("family", getFamilyOverview());
        result.put("health", getHealthOverview());
        return result;
    }

    private Map<String, Object> getMemberOverview() {
        Map<String, Object> map = new HashMap<>();
        map.put("genderCounts", memberClient.getGenderCounts());
        map.put("statusCounts", memberClient.getStatusCounts());
        map.put("ageGroupCounts", memberClient.getAgeGroupCounts());
        map.put("headCount", memberClient.getHeadMemberCount());
        return map;
    }

    private Map<String, Object> getFamilyOverview() {
        Map<String, Object> map = new HashMap<>();
        map.put("povertyCounts", familyClient.getPovertyCounts());
        map.put("localityCounts", familyClient.getLocalityCounts());
        return map;
    }

    private Map<String, Object> getHealthOverview() {
        Map<String, Object> map = new HashMap<>();
        map.put("pregnantCount", healthClient.getPregnantCount());
        map.put("ncdCounts", healthClient.getNcdCounts());
        return map;
    }
}
