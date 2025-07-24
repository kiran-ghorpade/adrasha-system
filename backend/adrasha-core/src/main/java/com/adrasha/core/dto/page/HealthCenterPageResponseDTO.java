package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.HealthCenterResponseDTO;

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