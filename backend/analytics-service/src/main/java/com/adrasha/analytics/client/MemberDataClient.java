package com.adrasha.analytics.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.adrasha.core.filter.dto.MemberDataFilterDTO;
import com.adrasha.core.response.dto.MemberDataResponseDTO;


@FeignClient(name = "data-service", contextId = "MemberDataClient" , path = "/data/members")
public interface MemberDataClient {
	
    @GetMapping
    Page<MemberDataResponseDTO> getAll(@SpringQueryMap MemberDataFilterDTO filterDTO,@SpringQueryMap Pageable pageable);

    @GetMapping("/count")
    Map<String, Long> getCount(@SpringQueryMap MemberDataFilterDTO filterDTO);
}
