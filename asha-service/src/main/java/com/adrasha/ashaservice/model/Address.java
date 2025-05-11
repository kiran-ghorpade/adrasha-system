package com.adrasha.ashaservice.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Address {

	private UUID id;

	@NotBlank
	private String village;

	@NotBlank
	private String tehsil;

	@NotBlank
	private String district;

	@NotBlank
	private String state;
	
	@NotBlank
	private String country= "india";
	
	@NotBlank
	@Size(max = 6, min = 6)
	private String pincode;
	
}
