package com.adrasha.masterdata.health.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.base.controller.MasterController;
import com.adrasha.masterdata.health.dto.HealthCenterFilterDTO;
import com.adrasha.masterdata.health.model.HealthCenter;
import com.adrasha.masterdata.health.service.HealthCenterService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/healthCenters")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Health Center Management")
public class HealthCenterController extends MasterController<HealthCenter> {

	private HealthCenterService healthCenterService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<Response<Page<HealthCenter>>> getAll(
			@RequestParam(required = false)
			HealthCenterFilterDTO filterDTO,
			@RequestParam(required = false)
			@PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		HealthCenter searchTerms = mapper.map(filterDTO, HealthCenter.class);
		
		Example<HealthCenter> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Page<HealthCenter> page = healthCenterService.getAll(example, pageable);

		Response<Page<HealthCenter>> apiResponse = Response.<Page<HealthCenter>>builder()
				.status(HttpStatus.OK.value())
				.message("List")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}
}
