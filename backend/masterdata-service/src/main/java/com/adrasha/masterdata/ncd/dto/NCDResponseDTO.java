package com.adrasha.masterdata.ncd.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class NCDResponseDTO {
	
	private UUID id;

	private String name;
	
	private String description;
}
