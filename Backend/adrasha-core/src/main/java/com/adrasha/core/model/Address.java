package com.adrasha.core.model;

import lombok.Data;

@Data
public class Address {

	private Long id;

	private String village;

	private String tehsil;

	private String district;

	private String state;

	private String country= "india";
	
	private String pincode;
	
}
