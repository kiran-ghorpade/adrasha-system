package com.adrasha.data.member.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.data.member.repository.MemberRepository;
import com.adrasha.data.member.service.MemberReportService;

@Service
public class MemberReportServiceImpl implements MemberReportService {

    private final MemberRepository memberRepository;

    public MemberReportServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberReportDTO> getAllMembers() {
        return memberRepository.findAllMembersForReport();
    }

    @Override
    public List<MemberReportDTO> getMembersByAgeRange(int minAge, int maxAge) {
        return memberRepository.findMembersByAgeRangeForReport(minAge, maxAge);
    }

    @Override
    public List<MemberReportDTO> getMembersByGender(Gender gender) {
        return memberRepository.findMembersByGenderForReport(gender);
    }

    @Override
    public List<MemberReportDTO> getMembersByStatus(AliveStatus status) {
        return memberRepository.findMembersByStatusForReport(status);
    }
}
