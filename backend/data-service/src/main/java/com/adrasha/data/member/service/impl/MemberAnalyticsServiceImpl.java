package com.adrasha.data.member.service.impl;

import com.adrasha.core.model.Gender;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.data.member.repository.MemberRepository;
import com.adrasha.data.member.service.MemberAnalyticsService;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberAnalyticsServiceImpl implements MemberAnalyticsService {

    private final MemberRepository memberRepository;

    public MemberAnalyticsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Map<Gender, Long> getGenderCounts() {
        // JPQL query groups by gender
        return memberRepository.countByGender();
    }

    @Override
    public Map<AliveStatus, Long> getStatusCounts() {
        // JPQL query groups by alive status
        return memberRepository.countByAliveStatus();
    }

    @Override
    public Map<String, Long> getAgeGroupCounts() {
        // Returns a map of age group name to count
        List<Object[]> results = memberRepository.countByAgeGroup();
        Map<String, Long> counts = new HashMap<>();	
        for (Object[] row : results) {
            counts.put((String) row[0], (Long) row[1]);
        }
        return counts;
    }
    @Override
    public long getHeadMemberCount() {
        return memberRepository.countByIsFamilyHeadTrue();
    }
}
