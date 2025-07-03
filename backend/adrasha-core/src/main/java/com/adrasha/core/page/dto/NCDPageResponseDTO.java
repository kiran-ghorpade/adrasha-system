package com.adrasha.core.page.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.response.dto.NCDResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class NCDPageResponseDTO extends PageImpl<NCDResponseDTO> {

	private static final long serialVersionUID = 1L;

	public NCDPageResponseDTO(List<NCDResponseDTO> content) {
		super(content);
	}

	public NCDPageResponseDTO() {
		super(List.of());
	}

}
