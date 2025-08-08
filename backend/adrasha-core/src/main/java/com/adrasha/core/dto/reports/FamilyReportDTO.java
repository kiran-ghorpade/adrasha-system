package com.adrasha.core.dto.reports;

import com.adrasha.core.model.PovertyStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FamilyReportDTO {

	private Integer houseId;
	
	private String headMemberName;

	private PovertyStatus povertyStatus;
}
