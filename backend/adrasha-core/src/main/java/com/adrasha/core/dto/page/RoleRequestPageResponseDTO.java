package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.RoleRequestResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class RoleRequestPageResponseDTO extends PageImpl<RoleRequestResponseDTO> {

	private static final long serialVersionUID = 1L;

	public RoleRequestPageResponseDTO(List<RoleRequestResponseDTO> content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

	public RoleRequestPageResponseDTO() {
		super(List.of());
	}

}
