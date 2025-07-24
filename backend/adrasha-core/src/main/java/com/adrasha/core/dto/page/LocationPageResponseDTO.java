package com.adrasha.core.dto.page;

import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.adrasha.core.dto.response.LocationResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema
public class LocationPageResponseDTO  extends PageImpl<LocationResponseDTO> {

	private static final long serialVersionUID = 1L;

	public LocationPageResponseDTO(List<LocationResponseDTO> content) {
		super(content);
	}
	
	public LocationPageResponseDTO() {
		super(List.of());
	}
}
