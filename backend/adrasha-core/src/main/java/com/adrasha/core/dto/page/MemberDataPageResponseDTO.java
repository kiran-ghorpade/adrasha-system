package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.MemberDataResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema
public class MemberDataPageResponseDTO  extends PageImpl<MemberDataResponseDTO> {

	private static final long serialVersionUID = 1L;

	public MemberDataPageResponseDTO(List<MemberDataResponseDTO> content) {
		super(content);
	}
	
	public MemberDataPageResponseDTO() {
		super(List.of());
	}
}
