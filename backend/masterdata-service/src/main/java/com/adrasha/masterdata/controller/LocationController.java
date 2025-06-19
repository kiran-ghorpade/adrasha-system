package com.adrasha.masterdata.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.masterdata.location.dto.LocationCreateDTO;
import com.adrasha.masterdata.location.dto.LocationFilterDTO;
import com.adrasha.masterdata.location.dto.LocationResponseDTO;
import com.adrasha.masterdata.location.dto.LocationUpdateDTO;
import com.adrasha.masterdata.model.Location;
import com.adrasha.masterdata.service.LocationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/locations")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Location Management")
public class LocationController {


	@Autowired
    private LocationService healthService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public Page<LocationResponseDTO> getAllLocations(
            LocationFilterDTO filterDTO,
            @PageableDefault(page = 0, size = 5, sort = "name") 
    		Pageable pageable
    	) {

        Location exampleRecord = mapper.map(filterDTO, Location.class);

        Example<Location> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

        Page<Location> healthPage = healthService.getAll(example, pageable);

        return healthPage.map(record -> mapper.map(record, LocationResponseDTO.class));
    }

    @GetMapping("/{id}")
    public LocationResponseDTO getLocation(@PathVariable UUID id) {
        Location health = healthService.get(id);
        return mapper.map(health, LocationResponseDTO.class);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LocationResponseDTO> createLocation(@Valid @RequestBody LocationCreateDTO healthCreateDTO) {
        Location health = mapper.map(healthCreateDTO, Location.class);
        Location created = healthService.create(health);
        LocationResponseDTO dto = mapper.map(created, LocationResponseDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LocationResponseDTO updateLocation(@PathVariable UUID id, @Valid @RequestBody LocationUpdateDTO updatedLocation) {
        Location health = mapper.map(updatedLocation, Location.class);
        Location updated = healthService.update(id, health);
        return mapper.map(updated, LocationResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) {
        healthService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
