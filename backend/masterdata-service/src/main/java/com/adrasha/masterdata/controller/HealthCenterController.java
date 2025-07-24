package com.adrasha.masterdata.controller;

import java.net.URI;
import java.util.Map;
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

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.dto.filter.HealthCenterFilterDTO;
import com.adrasha.core.dto.page.HealthCenterPageResponseDTO;
import com.adrasha.core.dto.response.HealthCenterResponseDTO;
import com.adrasha.masterdata.dto.HealthCenterCreateDTO;
import com.adrasha.masterdata.dto.HealthCenterUpdateDTO;
import com.adrasha.masterdata.model.HealthCenter;
import com.adrasha.masterdata.service.HealthCenterService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/healthCenters")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "HealthCenter")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('USER', 'SYSTEM')")
public class HealthCenterController{

	@Autowired
    private HealthCenterService healthService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthCenterPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    public Page<HealthCenterResponseDTO> getAllHealthCenters(
            HealthCenterFilterDTO filterDTO,
            @PageableDefault(page = 0, size = 5, sort = "name") 
    		Pageable pageable
    	) {

        HealthCenter exampleRecord = mapper.map(filterDTO, HealthCenter.class);

        Example<HealthCenter> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

        Page<HealthCenter> healthPage = healthService.getAll(example, pageable);

        return healthPage.map(record -> mapper.map(record, HealthCenterResponseDTO.class));
    }
    
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Map<String, Long> getTotalCount(HealthCenterFilterDTO filterDTO) {
		HealthCenter filter = mapper.map(filterDTO, HealthCenter.class);

		Example<HealthCenter> example = Example.of(filter, ExampleMatcherUtils.getDefaultMatcher());

		long total = healthService.getCount(example);
		return Map.of("count", total);
	}


    @GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthCenterResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public HealthCenterResponseDTO getHealthCenter(@PathVariable UUID id) {
        HealthCenter health = healthService.get(id);
        return mapper.map(health, HealthCenterResponseDTO.class);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HealthCenterResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<HealthCenterResponseDTO> createHealthCenter(@Valid @RequestBody HealthCenterCreateDTO healthCreateDTO) {
        HealthCenter health = mapper.map(healthCreateDTO, HealthCenter.class);
        HealthCenter created = healthService.create(health);
        HealthCenterResponseDTO dto = mapper.map(created, HealthCenterResponseDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthCenterResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public HealthCenterResponseDTO updateHealthCenter(@PathVariable UUID id, @Valid @RequestBody HealthCenterUpdateDTO updatedHealth) {
        HealthCenter health = mapper.map(updatedHealth, HealthCenter.class);
        HealthCenter updated = healthService.update(id, health);
        return mapper.map(updated, HealthCenterResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> deleteHealthCenter(@PathVariable UUID id) {
        healthService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
