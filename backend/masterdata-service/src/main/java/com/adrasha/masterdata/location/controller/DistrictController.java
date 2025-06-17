package com.adrasha.masterdata.location.controller;

import java.util.UUID;

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
import com.adrasha.masterdata.location.model.District;
import com.adrasha.masterdata.location.model.State;
import com.adrasha.masterdata.location.service.DistrictService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/districts")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "District Management")
public class DistrictController extends MasterController<District>{

	@Autowired
	private DistrictService districtService;

	@GetMapping
	public ResponseEntity<Response<Page<District>>> getAll(
		 	@RequestParam(required = false) UUID stateId,
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		State state= new State();
		state.setId(stateId);
		
		District district = new District();
		district.setState(state);
		
		Example<District> example = Example.of(district, ExampleMatcherUtils.getDefaultMatcher());

		Page<District> page = districtService.getAll(example, pageable);

		Response<Page<District>> apiResponse = Response.<Page<District>>builder()
				.status(HttpStatus.OK.value())
				.message("List")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

}
