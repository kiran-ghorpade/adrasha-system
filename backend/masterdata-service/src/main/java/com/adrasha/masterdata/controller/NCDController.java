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
import com.adrasha.core.filter.dto.NCDFilterDTO;
import com.adrasha.core.page.dto.NCDPageResponseDTO;
import com.adrasha.core.response.dto.NCDResponseDTO;
import com.adrasha.masterdata.dto.NCDCreateDTO;
import com.adrasha.masterdata.dto.NCDUpdateDTO;
import com.adrasha.masterdata.model.NCD;
import com.adrasha.masterdata.service.NCDService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/masterdata/ncd")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "NonCommunicableDisease Management")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('USER','SYSTEM')")
public class NCDController {
	

	@Autowired
    private NCDService ncdService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = NCDPageResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    public Page<NCDResponseDTO> getAllNCD(
            NCDFilterDTO filterDTO,
            @PageableDefault(page = 0, size = 5, sort = "name") 
    		Pageable pageable
    	) {

        NCD exampleRecord = mapper.map(filterDTO, NCD.class);

        Example<NCD> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

        Page<NCD> healthPage = ncdService.getAll(example, pageable);

        return healthPage.map(record -> mapper.map(record, NCDResponseDTO.class));
    }
    
	@GetMapping("/count")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	public Map<String, Long> getCount(NCDFilterDTO filterDTO) {
		NCD filter = mapper.map(filterDTO, NCD.class);

		Example<NCD> example = Example.of(filter, ExampleMatcherUtils.getDefaultMatcher());

		long total = ncdService.getCount(example);
		return Map.of("count", total);
	}


    @GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = NCDResponseDTO.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public NCDResponseDTO getNCD(@PathVariable UUID id) {
        NCD health = ncdService.get(id);
        return mapper.map(health, NCDResponseDTO.class);
    }

    @PostMapping
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = NCDResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NCDResponseDTO> createNCD(@Valid @RequestBody NCDCreateDTO healthCreateDTO) {
        NCD health = mapper.map(healthCreateDTO, NCD.class);
        NCD created = ncdService.create(health);
        NCDResponseDTO dto = mapper.map(created, NCDResponseDTO.class);

        URI NCD = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(NCD).body(dto);
    }

    @PutMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = NCDResponseDTO.class)))
	@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    public NCDResponseDTO updateNCD(@PathVariable UUID id, @Valid @RequestBody NCDUpdateDTO updatedNCD) {
        NCD health = mapper.map(updatedNCD, NCD.class);
        NCD updated = ncdService.update(id, health);
        return mapper.map(updated, NCDResponseDTO.class);
    }

    @DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204", content = @Content())
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNCD(@PathVariable UUID id) {
        ncdService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
