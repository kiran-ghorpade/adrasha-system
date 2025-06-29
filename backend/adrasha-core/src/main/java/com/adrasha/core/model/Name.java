package com.adrasha.core.model;

import com.adrasha.core.utils.UpperCaseConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Name {

	@NotBlank
	@Size(min = 2, max=100)
	@Convert(converter = UpperCaseConverter.class)
	private String firstname;
	
	@Size(min = 2, max=100)
	@Convert(converter = UpperCaseConverter.class)
	private String middlename;

	@NotBlank
	@Size(min = 2, max=100)
	@Convert(converter = UpperCaseConverter.class)
	private String lastname;
}
