package com.adrasha.core.dto;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleMatcherUtils {

	public static <T> ExampleMatcher getDefaultMatcher() {
		return ExampleMatcher.matchingAll()
				.withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
				.withIgnoreCase();
	}
}
