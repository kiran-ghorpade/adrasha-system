package com.adrasha.core.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResult<T> {
	private List<T> content;
	private int page;
	private long pageSize;
	private long totalPages;
	private long totalItems;
}
