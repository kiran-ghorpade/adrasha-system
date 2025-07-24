package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class UserPageResponseDTO extends PageImpl<UserResponseDTO> {

	private static final long serialVersionUID = 1L;

	public UserPageResponseDTO(List<UserResponseDTO> content) {
		super(content);
	}
	
	public UserPageResponseDTO() {
		super(List.of());
	}
}
