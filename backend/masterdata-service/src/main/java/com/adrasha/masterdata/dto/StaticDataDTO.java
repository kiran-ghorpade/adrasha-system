package com.adrasha.masterdata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class StaticDataDTO {
	
	private String code;
	
	private String label;

}
