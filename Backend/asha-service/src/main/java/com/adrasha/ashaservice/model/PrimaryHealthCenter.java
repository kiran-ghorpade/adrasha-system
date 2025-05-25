package com.adrasha.ashaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimaryHealthCenter {

	private String name;
	private String description;
	private Address address;
}
