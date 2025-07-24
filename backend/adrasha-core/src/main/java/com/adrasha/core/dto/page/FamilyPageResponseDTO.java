package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.FamilyDataResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class FamilyPageResponseDTO extends PageImpl<FamilyDataResponseDTO> {

	private static final long serialVersionUID = 1L;

	public FamilyPageResponseDTO(List<FamilyDataResponseDTO> content) {
		super(content);
	}

	public FamilyPageResponseDTO() {
		super(List.of()); // Required for Swagger 
	}
	
	
}