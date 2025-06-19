package com.adrasha.masterdata.ncd.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NCDUpdateDTO {
	
	@NotBlank
	private String name;
	
	private String description;
}
