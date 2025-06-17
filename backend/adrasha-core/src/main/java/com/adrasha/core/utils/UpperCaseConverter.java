package com.adrasha.core.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UpperCaseConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return attribute.toUpperCase();
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}

}
