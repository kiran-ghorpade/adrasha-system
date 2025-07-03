package com.adrasha.masterdata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema
public class NCDCreateDTO {
	
	@NotBlank
	private String name;
	
	private String description;
}
