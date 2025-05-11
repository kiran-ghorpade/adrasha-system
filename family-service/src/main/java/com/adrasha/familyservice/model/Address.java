package com.adrasha.familyservice.model;

import java.util.UUID;

import com.adrasha.familyservice.convertor.UpperCaseConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String village;

	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String tehsil;

	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String district;

	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String state;
	
	@NotBlank
	@Convert(converter = UpperCaseConverter.class)
	private String country= "india";
	
	@NotBlank
	@Size(max = 6, min = 6)
	@Column(unique = true)
	private String pincode;
	
}
