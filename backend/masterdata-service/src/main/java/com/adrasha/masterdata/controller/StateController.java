package com.adrasha.masterdata.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.dto.StateDTO;
import com.adrasha.masterdata.model.State;
import com.adrasha.masterdata.service.StateService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/states")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "State Management")
public class StateController {

	@Autowired
	private StateService stateService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Page<State>>> getAllStates(
			@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
			Pageable pageable,
			@RequestParam(required = false)
			UUID countryId
			){

		Page<State> page = stateService.getAllStates(pageable, countryId);

		Response<Page<State>> apiResponse = Response.<Page<State>>builder()
				.status(HttpStatus.OK.value())
				.message("List of States")
				.payload(page)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<StateDTO>> getstate(@Parameter(description = "ID of the state to retrieve", required = true) @PathVariable UUID id){

		State state = stateService.getState(id);
		StateDTO dto = mapper.map(state, StateDTO.class);

		Response<StateDTO> apiResponse = Response.<StateDTO>builder()
				.status(HttpStatus.OK.value())
				.message("State Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<StateDTO>> createState(@Valid @RequestBody StateDTO newState){

		State state = mapper.map(newState, State.class);
		state = stateService.createState(state);
		StateDTO dto = mapper.map(state, StateDTO.class);

		Response<StateDTO> apiResponse = Response.<StateDTO>builder()
				.status(HttpStatus.CREATED.value())
				.message("State created with id "+ state.getId())
				.payload(dto)
				.build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(state.getId())
				.toUri();

		return ResponseEntity.created(location).body(apiResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<StateDTO>> udpateState(@Parameter(description = "ID of the state to retrieve", required = true) @PathVariable UUID id, @Valid @RequestBody StateDTO updatedState){

		State state = mapper.map(updatedState, State.class);
		state = stateService.updateState(id, state);
		StateDTO dto = mapper.map(state, StateDTO.class);

		Response<StateDTO> apiResponse = Response.<StateDTO>builder()
				.status(HttpStatus.OK.value())
				.message("State Details with id "+ id)
				.payload(dto)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteState(@PathVariable UUID id){

		stateService.deleteState(id);

		return ResponseEntity.noContent().build();
	}

}
