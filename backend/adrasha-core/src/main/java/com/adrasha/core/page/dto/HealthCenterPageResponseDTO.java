package com.adrasha.core.page.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.response.dto.HealthCenterResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema
public class HealthCenterPageResponseDTO  extends PageImpl<HealthCenterResponseDTO> {

	private static final long serialVersionUID = 1L;

	public HealthCenterPageResponseDTO(List<HealthCenterResponseDTO> content) {
		super(content);
	}

	public HealthCenterPageResponseDTO() {
		super(List.of());
	}
	
	
	
}