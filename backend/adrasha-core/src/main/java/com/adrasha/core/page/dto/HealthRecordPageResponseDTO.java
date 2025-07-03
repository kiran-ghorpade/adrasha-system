package com.adrasha.core.page.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.response.dto.HealthRecordResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema
public class HealthRecordPageResponseDTO  extends PageImpl<HealthRecordResponseDTO> {

	private static final long serialVersionUID = 1L;

	public HealthRecordPageResponseDTO(List<HealthRecordResponseDTO> content) {
		super(content);
	}
	
	public HealthRecordPageResponseDTO() {
		super(List.of());
	}
	

}
