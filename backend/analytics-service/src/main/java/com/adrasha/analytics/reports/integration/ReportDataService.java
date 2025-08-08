package com.adrasha.analytics.reports.integration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.reports.client.DataServiceClient;
import com.adrasha.analytics.reports.dto.ReportData;
import com.adrasha.core.dto.filter.FamilyFilterDTO;
import com.adrasha.core.dto.filter.HealthRecordFilterDTO;
import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.core.dto.reports.FamilyReportDTO;
import com.adrasha.core.dto.reports.HealthRecordReportDTO;
import com.adrasha.core.dto.reports.MemberReportDTO;

@Service
public class ReportDataService {

	@Autowired
	private DataServiceClient dataClient;

	private static final DateTimeFormatter DATE_FORMATTER =
		    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
		                     .withZone(ZoneId.systemDefault());
	
	// family
	public ReportData fetchFamilyData(UUID ashaId) {
	    
	    List<FamilyReportDTO> reportData = Optional.ofNullable(
	    	    dataClient.getAll(FamilyFilterDTO.builder()
	    	    		.ashaId(ashaId)
	    	    		.build())
	    	).orElseGet(List::of);

	    List<String> headers = List.of("Sr.No.","House Id", "Head Name", "Poverty Status");

	    AtomicInteger counter = new AtomicInteger(1);

	    List<List<Object>> rows = reportData.stream()
	        .map(family -> mapFamilyToRow(family, counter.getAndIncrement()))
	        .toList();

	    return ReportData.builder()
	    		.headers(headers)
	    		.rows(rows)
	    		.build();
	}

	private List<Object> mapFamilyToRow(FamilyReportDTO family, int serialNumber) {
	    return List.of(
	        serialNumber,
	        family.getHouseId(),
	        family.getHeadMemberName(),
	        family.getPovertyStatus()
	    );
	}
	
	// Member
	public ReportData fetchMemberData(UUID ashaId) {
	    
	    List<MemberReportDTO> reportData = Optional.ofNullable(
	        dataClient.getAll(MemberFilterDTO.builder()
	            .ashaId(ashaId)
	            .build())
	    ).orElseGet(List::of);

	    List<String> headers = List.of(
	        "Sr.No.",
	        "Name",
	        "Age",
	        "Gender",
	        "Alive Status",
	        "Date of Birth",
	        "Adhar Number",
	        "Abha Number",
	        "Mobile Number"
	    );

	    AtomicInteger counter = new AtomicInteger(1);

	    List<List<Object>> rows = reportData.stream()
	        .map(member -> mapMemberToRow(member, counter.getAndIncrement()))
	        .toList();

	    return ReportData.builder()
	        .headers(headers)
	        .rows(rows)
	        .build();
	}

	private List<Object> mapMemberToRow(MemberReportDTO member, int serialNumber) {
	    return List.of(
	        serialNumber,
	        member.getName(),
	        member.getAge(),
	        member.getGender(),
	        member.getAliveStatus(),
	        member.getDateOfBirth().toString(),
	        member.getAdharNumber(),
	        member.getAbhaNumber(),
	        member.getMobileNumber()
	    );
	}

	
	// health
	public ReportData fetchHealthData(UUID ashaId) {
	    
	    List<HealthRecordReportDTO> reportData = Optional.ofNullable(
	        dataClient.getAll(HealthRecordFilterDTO.builder()
	            .ashaId(ashaId)
	            .build())
	    ).orElseGet(List::of);

	    List<String> headers = List.of(
	        "Sr.No.",
	        "Member Name",
	        "Recorded At",
	        "Pregnant",
	        "Height (cm)",
	        "Weight (kg)",
	        "NCD Conditions"
	    );

	    AtomicInteger counter = new AtomicInteger(1);

	    List<List<Object>> rows = reportData.stream()
	        .map(health -> mapHealthToRow(health, counter.getAndIncrement()))
	        .toList();

	    return ReportData.builder()
	        .headers(headers)
	        .rows(rows)
	        .build();
	}

	private List<Object> mapHealthToRow(HealthRecordReportDTO health, int serialNumber) {
	    return List.of(
	        serialNumber,
	        health.getMemberName(),
	        DATE_FORMATTER.format(health.getRecordedAt()),
	        health.getPregnant(),
	        health.getHeight(),
	        health.getWeight(),
	        formatNcdList(health.getNCDList())
	    );
	}

	private String formatNcdList(Set<UUID> ncdList) {
	    return (ncdList == null || ncdList.isEmpty()) ? "None" :
	           ncdList.stream().map(UUID::toString).reduce((a, b) -> a + ", " + b).orElse("None");
	}

}
