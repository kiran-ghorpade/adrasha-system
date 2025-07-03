package com.adrasha.masterdata.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.model.AgeGroup;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.HealthCenterType;
import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.core.model.RequestStatus;
import com.adrasha.core.model.Role;
import com.adrasha.masterdata.dto.StaticDataDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/masterdata")
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "StaticData")
public class StaticDataController {
	
	@GetMapping("/request-statuses")
	public List<StaticDataDTO> getRequestStatus() {
		return Arrays.stream(RequestStatus.values())
				.map(request -> 
						StaticDataDTO.builder()
						.code(request.name())
						.label(request.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/roles")
	public List<StaticDataDTO> getRoles() {
		return Arrays.stream(Role.values())
				.map(role -> 
						StaticDataDTO.builder()
						.code(role.name())
						.label(role.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}

	@GetMapping("/genders")
	public List<StaticDataDTO> getGenders() {
		return Arrays.stream(Gender.values())
				.map(gender -> 
						StaticDataDTO.builder()
						.code(gender.name())
						.label(gender.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/location-types")
	public List<StaticDataDTO> getLocationTypes() {
		return Arrays.stream(LocalityType.values())
				.map(locationType -> 
						StaticDataDTO.builder()
						.code(locationType.name())
						.label(locationType.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}	
	
	@GetMapping("/health-center-types")
	public List<StaticDataDTO> getHealthCenterTypes() {
		return Arrays.stream(HealthCenterType.values())
				.map(center -> 
						StaticDataDTO.builder()
						.code(center.name())
						.label(center.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}	
	
	@GetMapping("/age-groups")
	public List<StaticDataDTO> getAgeGroups() {
		return Arrays.stream(AgeGroup.values())
				.map(ageGroup -> 
						StaticDataDTO.builder()
						.code(ageGroup.name())
						.label(ageGroup.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/poverty-statuses")
	public List<StaticDataDTO> getPovertyStatuses(){
		return Arrays.stream(PovertyStatus.values())
				.map(status -> 
						StaticDataDTO.builder()
						.code(status.name())
						.label(status.getDisplayName())
						.build())
				.collect(Collectors.toList());
	}
}
