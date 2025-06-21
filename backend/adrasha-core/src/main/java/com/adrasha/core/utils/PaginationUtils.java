package com.adrasha.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PaginationUtils {

	public static <T, P> List<T> getAllRecords(BiFunction<P, PageRequest, Page<T>> clientCall, P filter, Sort sort){
		int page = 0;
		int size = 100;
		
		List<T> data = new ArrayList<>();
		
		Page<T> currentPage;
		
		do {
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            
			currentPage = clientCall.apply(filter, pageRequest);
			
			data.addAll(currentPage.getContent());

			page++;
			
		} while (currentPage.hasNext());
		
		return data;
	}
	
}
