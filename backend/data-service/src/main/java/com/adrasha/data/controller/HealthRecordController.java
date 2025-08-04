package com.adrasha.data.controller;

import java.net.URI;
import java.util.UUID;

import org.modelmapper.ModelMapper;
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
import com.adrasha.core.dto.filter.FamilyDataFilterDTO;
import com.adrasha.core.dto.filter.HealthRecordFilterDTO;
import com.adrasha.core.dto.page.HealthRecordPageResponseDTO;
import com.adrasha.core.dto.response.HealthRecordResponseDTO;
import com.adrasha.data.health.records.dto.HealthRecordCreateDTO;
import com.adrasha.data.health.records.dto.HealthRecordUpdateDTO;
import com.adrasha.data.model.Family;
import com.adrasha.data.model.HealthRecord;
import com.adrasha.data.service.HealthRecordService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data/health/records")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "HealthRecord")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('ASHA','SYSTEM')")
public class HealthRecordController {

	// Dependencies
    private HealthRecordService healthService;
    private ModelMapper mapper;

    // Get All Records
    @GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthRecordPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    public Page<HealthRecordResponseDTO> getHealthRecordPage(
            HealthRecordFilterDTO filterDTO,
            @PageableDefault(page = 0, size = 5, sort = "createdAt") 
    		Pageable pageable
    	) {

        HealthRecord exampleRecord = mapper.map(filterDTO, HealthRecord.class);

        Example<HealthRecord> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

        Page<HealthRecord> healthPage = healthService.getHealthRecordPage(example, pageable);

        return healthPage.map(record -> mapper.map(record, HealthRecordResponseDTO.class));
    }
    
    // Get Count
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Long getHealthRecordCount(HealthRecordFilterDTO filterDTO){
		
		HealthRecord searchTerms = mapper.map(filterDTO, HealthRecord.class);
		
		Example<HealthRecord> exampleHealthRecord = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());

		Long count = healthService.getHealthRecordCount(exampleHealthRecord);
				
		return count;
	}
	

    // Get a Record    
    @GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthRecordResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public HealthRecordResponseDTO getHealthRecord(@PathVariable UUID id) {
        HealthRecord health = healthService.getHealthRecord(id);
        return mapper.map(health, HealthRecordResponseDTO.class);
    }

    // Create new Record
    @PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HealthRecordResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<HealthRecordResponseDTO> createHealth(@Valid @RequestBody HealthRecordCreateDTO healthCreateDTO) {
        HealthRecord health = mapper.map(healthCreateDTO, HealthRecord.class);
        HealthRecord created = healthService.createHealthRecord(health);
        HealthRecordResponseDTO dto = mapper.map(created, HealthRecordResponseDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    // Update a Record
    @PutMapping("/{id}")	
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = HealthRecordResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))	
    public HealthRecordResponseDTO updateHealthRecord(@PathVariable UUID id, @Valid @RequestBody HealthRecordUpdateDTO updatedHealth) {
        HealthRecord health = mapper.map(updatedHealth, HealthRecord.class);
        HealthRecord updated = healthService.updateHealthRecord(id, health);
        return mapper.map(updated, HealthRecordResponseDTO.class);
    }

    // Delete a Record
    @DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))	
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable UUID id) {
        healthService.deleteHealthRecord(id);
        return ResponseEntity.noContent().build();
    }
}
