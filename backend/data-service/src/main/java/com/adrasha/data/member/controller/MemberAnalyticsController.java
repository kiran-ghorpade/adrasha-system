package com.adrasha.data.member.controller;

import com.adrasha.core.model.Gender;
import com.adrasha.data.member.service.MemberAnalyticsService;
import com.adrasha.core.model.AliveStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/data/member/analytics")
public class MemberAnalyticsController {

    private final MemberAnalyticsService memberAnalyticsService;

    public MemberAnalyticsController(MemberAnalyticsService memberAnalyticsService) {
        this.memberAnalyticsService = memberAnalyticsService;
    }

    @GetMapping("/gender")
    public Map<Gender, Long> getGenderCounts() {
        return memberAnalyticsService.getGenderCounts();
    }

    @GetMapping("/status")
    public Map<AliveStatus, Long> getStatusCounts() {
        return memberAnalyticsService.getStatusCounts();
    }

    @GetMapping("/age-group")
    public Map<String, Long> getAgeGroupCounts() {
        return memberAnalyticsService.getAgeGroupCounts();
    }

    @GetMapping("/head-count")
    public long getHeadMemberCount() {
        return memberAnalyticsService.getHeadMemberCount();
    }
}
