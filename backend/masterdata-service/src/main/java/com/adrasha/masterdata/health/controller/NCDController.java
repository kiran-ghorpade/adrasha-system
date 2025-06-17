package com.adrasha.masterdata.health.controller;

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
import com.adrasha.masterdata.health.model.NonCommunicableDisease;
import com.adrasha.masterdata.health.service.NCDService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/ncd")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "NonCommunicableDisease Management")
public class NCDController extends MasterController<NonCommunicableDisease>{
	
	@Autowired
	private NCDService ncdService;

	@GetMapping
	public ResponseEntity<Response<Page<NonCommunicableDisease>>> getAll(
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.DESC)
			Pageable pageable
			){

		Page<NonCommunicableDisease> page = ncdService.getAll(pageable);

		Response<Page<NonCommunicableDisease>> apiResponse = Response.<Page<NonCommunicableDisease>>builder()
				.status(HttpStatus.OK.value())
				.message("List")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

}
