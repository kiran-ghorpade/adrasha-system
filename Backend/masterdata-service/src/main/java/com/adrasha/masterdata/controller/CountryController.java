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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adrasha.core.dto.Response;
import com.adrasha.masterdata.dto.CountryDTO;
import com.adrasha.masterdata.model.Country;
import com.adrasha.masterdata.service.CountryService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/countries")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Country Management")
public class CountryController {
	
		@Autowired
		private CountryService countryService;
		
		@Autowired
		private ModelMapper mapper;

		@GetMapping
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<Page<Country>>> getAllCountries(
				@Schema
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
	
			Page<Country> page = countryService.getAllCountries(pageable);
			
			Response<Page<Country>> apiResponse = Response.<Page<Country>>builder()
					.status(HttpStatus.OK.value())
					.message("Country of Countries")
					.payload(page)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@GetMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<CountryDTO>> getUser(@Parameter(description = "ID of the country to retrieve", required = true) @PathVariable UUID id){
			
			Country country = countryService.getCountry(id);
			CountryDTO dto = mapper.map(country, CountryDTO.class);
			
			Response<CountryDTO> apiResponse = Response.<CountryDTO>builder()
					.status(HttpStatus.OK.value())
					.message("Country Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}
		
		@PostMapping
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<CountryDTO>> createCountry(@Valid @RequestBody CountryDTO newCountry){
			
			Country country = mapper.map(newCountry, Country.class);
			country = countryService.createCountry(country);
			CountryDTO dto = mapper.map(country, CountryDTO.class);
			
			Response<CountryDTO> apiResponse = Response.<CountryDTO>builder()
					.status(HttpStatus.CREATED.value())
					.message("Country created with id "+ country.getId())
					.payload(dto)
					.build();
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(country.getId())
					.toUri();
			
			return ResponseEntity.created(location).body(apiResponse);
		}
		
		@PutMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Response<CountryDTO>> udpatedUser(@Parameter(description = "ID of the country to retrieve", required = true) @PathVariable UUID id, CountryDTO updatedCountry){
			
			Country country = mapper.map(updatedCountry, Country.class);
			country = countryService.updateCountry(id, country);
			CountryDTO dto = mapper.map(country, CountryDTO.class);
						
			Response<CountryDTO> apiResponse = Response.<CountryDTO>builder()
					.status(HttpStatus.OK.value())
					.message("Country Details with id "+ id)
					.payload(dto)
					.build();
			
			return ResponseEntity.ok(apiResponse);
		}

		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteUser(@PathVariable UUID id){

			countryService.deleteCountry(id);
						
			return ResponseEntity.noContent().build();
		}

}
