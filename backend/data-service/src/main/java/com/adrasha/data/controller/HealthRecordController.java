package com.adrasha.data.controller;

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
import com.adrasha.core.response.dto.HealthResponseDTO;
import com.adrasha.data.health.dto.HealthCreateDTO;
import com.adrasha.data.health.dto.HealthFilterDTO;
import com.adrasha.data.health.dto.HealthUpdateDTO;
import com.adrasha.data.model.HealthRecord;
import com.adrasha.data.service.HealthRecordService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/data/healthRecords")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Health Management")
@PreAuthorize("hasRole('ASHA')")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public Page<HealthResponseDTO> getAllHealthRecords(
            HealthFilterDTO filterDTO,
            @PageableDefault(page = 0, size = 5, sort = "createdAt") 
    		Pageable pageable
    	) {

        HealthRecord exampleRecord = mapper.map(filterDTO, HealthRecord.class);

        Example<HealthRecord> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

        Page<HealthRecord> healthPage = healthService.getAllRecords(example, pageable);

        return healthPage.map(record -> mapper.map(record, HealthResponseDTO.class));
    }

    @GetMapping("/{id}")
    public HealthResponseDTO getHealthRecord(@PathVariable UUID id) {
        HealthRecord health = healthService.getHealthRecord(id);
        return mapper.map(health, HealthResponseDTO.class);
    }

    @PostMapping
    public ResponseEntity<HealthResponseDTO> createHealth(@Valid @RequestBody HealthCreateDTO healthCreateDTO) {
        HealthRecord health = mapper.map(healthCreateDTO, HealthRecord.class);
        HealthRecord created = healthService.createHealthRecord(health);
        HealthResponseDTO dto = mapper.map(created, HealthResponseDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public HealthResponseDTO updateHealthRecord(@PathVariable UUID id, @Valid @RequestBody HealthUpdateDTO updatedHealth) {
        HealthRecord health = mapper.map(updatedHealth, HealthRecord.class);
        HealthRecord updated = healthService.updateHealthRecord(id, health);
        return mapper.map(updated, HealthResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable UUID id) {
        healthService.deleteHealthRecord(id);
        return ResponseEntity.noContent().build();
    }
}
