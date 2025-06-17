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
import com.adrasha.masterdata.location.model.Country;
import com.adrasha.masterdata.location.model.State;
import com.adrasha.masterdata.location.service.StateService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/masterdata/states")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "State Management")
public class StateController extends MasterController<Country>{


	@Autowired
	private StateService stateService;

	@GetMapping
	public ResponseEntity<Response<Page<State>>> getAll(UUID districtId,
			@Schema 
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) 
			Pageable pageable
	) {

		Country country = new Country();
		country.setId(districtId);

		State state = new State();
		state.setCountry(country);

		Example<State> example = Example.of(state, ExampleMatcherUtils.getDefaultMatcher());

		Page<State> page = stateService.getAll(example, pageable);

		Response<Page<State>> apiResponse = Response.<Page<State>>builder().status(HttpStatus.OK.value())
				.message("List").payload(page).build();

		return ResponseEntity.ok(apiResponse);
	}
	
}
