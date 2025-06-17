package com.adrasha.masterdata.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.base.controller.MasterController;
import com.adrasha.masterdata.location.model.Country;
import com.adrasha.masterdata.location.service.CountryService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/countries")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Country Management")
public class CountryController extends MasterController<Country>{

	@Autowired
	private CountryService countryService;

	@GetMapping
	public ResponseEntity<Response<Page<Country>>> getAll(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){

		Page<Country> page = countryService.getAll(pageable);

		Response<Page<Country>> apiResponse = Response.<Page<Country>>builder()
				.status(HttpStatus.OK.value())
				.message("List")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}
}
