package com.adrasha.masterdata.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Hidden
@RequestMapping("/masterdata/ncd")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "NCD")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('USER','SYSTEM')")
public class MasterdataAnalyticsController {

}
