package com.adrasha.data.member.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.data.member.service.MemberReportService;

@RestController
@RequestMapping("/data/member/reports")
public class MemberReportController {

    private final MemberReportService memberReportService;

    public MemberReportController(MemberReportService memberReportService) {
        this.memberReportService = memberReportService;
    }

    @GetMapping("/list")
    public List<MemberReportDTO> getAllMembers() {
        return memberReportService.getAllMembers();
    }

    @GetMapping("/age-group")
    public List<MemberReportDTO> getMembersByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        return memberReportService.getMembersByAgeRange(minAge, maxAge);
    }

    @GetMapping("/gender")
    public List<MemberReportDTO> getMembersByGender(@RequestParam Gender gender) {
        return memberReportService.getMembersByGender(gender);
    }

    @GetMapping("/status")
    public List<MemberReportDTO> getMembersByStatus(@RequestParam AliveStatus status) {
        return memberReportService.getMembersByStatus(status);
    }
}
