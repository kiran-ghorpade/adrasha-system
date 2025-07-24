package com.adrasha.data.member.service;

import com.adrasha.core.model.Gender;
import com.adrasha.core.model.AliveStatus;
import java.util.Map;

public interface MemberAnalyticsService {
    Map<Gender, Long> getGenderCounts();
    Map<AliveStatus, Long> getStatusCounts();
    Map<String, Long> getAgeGroupCounts();
    long getHeadMemberCount();
}
