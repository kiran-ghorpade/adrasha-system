package com.adrasha.core.dto.reports;

import java.time.LocalDate;
import java.util.UUID;

import com.adrasha.core.model.AliveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberReportDTO {
    private UUID id;
    private String name;
    private Integer age;
    private String gender;
    private AliveStatus aliveStatus;
	private LocalDate dateOfBirth;
	private String adharNumber;
	private String abhaNumber;
	private String mobileNumber;
}
