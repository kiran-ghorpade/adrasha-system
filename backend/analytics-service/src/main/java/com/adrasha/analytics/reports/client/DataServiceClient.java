package com.adrasha.analytics.reports.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.dto.filter.FamilyFilterDTO;
import com.adrasha.core.dto.filter.HealthRecordFilterDTO;
import com.adrasha.core.dto.filter.MemberFilterDTO;
import com.adrasha.core.dto.reports.FamilyReportDTO;
import com.adrasha.core.dto.reports.HealthRecordReportDTO;
import com.adrasha.core.dto.reports.MemberReportDTO;

@FeignClient(name = "data-service" , contextId = "DataServiceClient")
public interface DataServiceClient {
	
	   @GetMapping("/data/families/list")
	    List<FamilyReportDTO> getAll(@SpringQueryMap FamilyFilterDTO filterDTO);

		@GetMapping("/data/members/list")
		List<MemberReportDTO> getAll(@SpringQueryMap MemberFilterDTO filterDTO);

		@GetMapping("/data/health/records/list")
		List<HealthRecordReportDTO> getAll(@SpringQueryMap HealthRecordFilterDTO filterDTO);

}
