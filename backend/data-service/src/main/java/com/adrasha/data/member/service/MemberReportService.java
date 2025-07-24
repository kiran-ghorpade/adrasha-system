package com.adrasha.data.member.service;

import java.util.List;

import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;

public interface MemberReportService {
    List<MemberReportDTO> getAllMembers();

    List<MemberReportDTO> getMembersByAgeRange(int minAge, int maxAge);

    List<MemberReportDTO> getMembersByGender(Gender gender);

    List<MemberReportDTO> getMembersByStatus(AliveStatus status);
}
