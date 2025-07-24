package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.NCDResponseDTO;

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
