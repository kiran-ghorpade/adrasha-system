package com.adrasha.masterdata.base.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.base.model.MasterEntity;
import com.adrasha.masterdata.base.service.MasterService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

public abstract class MasterController<T extends MasterEntity>{

	@Autowired
	private MasterService<T> masterService;
	

	@GetMapping("/{id}")
	public ResponseEntity<Response<T>> get(@Parameter(description = "ID of the entity to retrieve", required = true) @PathVariable UUID id){

		T entity = masterService.get(id);

		Response<T> apiResponse = Response.<T>builder()
				.status(HttpStatus.OK.value())
				.message("Details with id "+ id)
				.payload(entity)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<T>> create(@Valid @RequestBody T newEntity){

		T entity = masterService.create(newEntity);
		
		Response<T> apiResponse = Response.<T>builder()
				.status(HttpStatus.CREATED.value())
				.message("created with id "+ entity.getId())
				.payload(entity)
				.build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(entity.getId())
				.toUri();

		return ResponseEntity.created(location).body(apiResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<T>> update(@PathVariable UUID id, @Valid @RequestBody T updatedEntity){

		T entity = masterService.update(id, updatedEntity);

		Response<T> apiResponse = Response.<T>builder()
				.status(HttpStatus.OK.value())
				.message("Details with id "+ id)
				.payload(entity)
				.build();

		return ResponseEntity.ok(apiResponse);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable UUID id){

		masterService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
