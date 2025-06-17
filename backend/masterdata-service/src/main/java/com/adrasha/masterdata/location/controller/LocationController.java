package com.adrasha.masterdata.location.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.base.controller.MasterController;
import com.adrasha.masterdata.location.dto.LocationFilterDTO;
import com.adrasha.masterdata.location.model.Location;
import com.adrasha.masterdata.location.model.Subdistrict;
import com.adrasha.masterdata.location.service.LocationService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/locations")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Location Management")
public class LocationController extends MasterController<Location> {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<Response<Page<Location>>> getAll(
			LocationFilterDTO filterDTO,
			@Schema
			@PageableDefault(page = 0, size = 5, sort = "name", direction = Sort.Direction.DESC)
			Pageable pageable
			){
		
		Subdistrict subdistrict = new Subdistrict();
		subdistrict.setId(filterDTO.getSubdistrict());
		
		Location location = mapper.map(filterDTO, Location.class);
		location.setSubdistrict(subdistrict);
		
		Example<Location> example = Example.of(location, ExampleMatcherUtils.getDefaultMatcher());

		Page<Location> page = locationService.getAll(example, pageable);

		Response<Page<Location>> apiResponse = Response.<Page<Location>>builder()
				.status(HttpStatus.OK.value())
				.message("List")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}
}
