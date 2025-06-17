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
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.base.controller.MasterController;
import com.adrasha.masterdata.location.model.District;
import com.adrasha.masterdata.location.model.Subdistrict;
import com.adrasha.masterdata.location.service.SubdistrictService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/subdistricts")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "SubDistrict Management")
public class SubdistrictController extends MasterController<Subdistrict> {

	@Autowired
	private SubdistrictService subdistrictService;

	@GetMapping
	public ResponseEntity<Response<Page<Subdistrict>>> getAll(UUID districtId,
			@Schema 
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) 
			Pageable pageable
	) {

		District district = new District();
		district.setId(districtId);

		Subdistrict subdistrict = new Subdistrict();
		subdistrict.setDistrict(district);

		Example<Subdistrict> example = Example.of(subdistrict, ExampleMatcherUtils.getDefaultMatcher());

		Page<Subdistrict> page = subdistrictService.getAll(example, pageable);

		Response<Page<Subdistrict>> apiResponse = Response.<Page<Subdistrict>>builder().status(HttpStatus.OK.value())
				.message("List").payload(page).build();

		return ResponseEntity.ok(apiResponse);
	}

}
