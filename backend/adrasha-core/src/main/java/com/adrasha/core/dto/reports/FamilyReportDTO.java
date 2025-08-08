package com.adrasha.core.dto.reports;

import com.adrasha.core.model.PovertyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FamilyReportDTO {

	private Integer houseId;
	
	private String headMemberName;

	private PovertyStatus povertyStatus;
}
