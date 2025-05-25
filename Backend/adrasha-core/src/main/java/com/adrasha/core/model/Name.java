package com.adrasha.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Name {

	@NotBlank
	@Size(min = 2)
	private String firstname;
	
	@Size(min = 2)
	private String middlename;

	@NotBlank
	@Size(min = 2)
	private String lastname;
}
