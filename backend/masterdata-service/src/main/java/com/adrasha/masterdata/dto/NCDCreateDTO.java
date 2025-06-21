package com.adrasha.masterdata.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NCDCreateDTO {
	
	@NotBlank
	private String name;
	
	private String description;
}
