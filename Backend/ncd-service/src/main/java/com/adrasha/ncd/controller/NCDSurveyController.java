package com.adrasha.ncd.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.ncd.dto.ApiResponse;
import com.adrasha.ncd.exception.NCDSurveyNotFoundException;
import com.adrasha.ncd.model.NCDSurvey;
import com.adrasha.ncd.service.NCDSurveyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/NCDSurvey")
public class NCDSurveyController {
	
	@Autowired
	private NCDSurveyService service;
	
//	@Autowired
//	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<NCDSurvey>>> getAllNCDSurvey(
		    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC)
			Pageable pageable
			){

		Page<NCDSurvey> NCDSurveyPage = service.getAllNCDSurveys(pageable);
//		Page<UserDTO> userDTOPage = usersPage.map(user -> mapper.map(user, UserDTO.class));
		
		ApiResponse<Page<NCDSurvey>> apiResponse = ApiResponse.<Page<NCDSurvey>>builder()
				.status(HttpStatus.OK.toString())
				.message("List of All NCDSurvey")
				.payload(NCDSurveyPage)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<NCDSurvey>> addNCDSurvey(@Valid @RequestBody NCDSurvey newNCDSurvey) throws NCDSurveyNotFoundException{
		
		NCDSurvey NCDSurvey = service.createNCDSurvey(newNCDSurvey);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<NCDSurvey> apiResponse = ApiResponse.<NCDSurvey>builder()
				.status(HttpStatus.OK.toString())
				.message("NCDSurvey Details Added. Id : "+ NCDSurvey.getId())
				.payload(NCDSurvey)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<NCDSurvey>> getNCDSurvey(@PathVariable UUID id) throws NCDSurveyNotFoundException{
		
		NCDSurvey NCDSurvey = service.getNCDSurveyById(id);
//		UserDTO dto = mapper.map(NCDSurvey, UserDTO.class);
		
		ApiResponse<NCDSurvey> apiResponse = ApiResponse.<NCDSurvey>builder()
				.status(HttpStatus.OK.toString())
				.message("NCDSurvey Details with id : "+ id)
				.payload(NCDSurvey)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<NCDSurvey>> udpateNCDSurvey(@PathVariable UUID id, @Valid @RequestBody NCDSurvey updatedNCDSurvey) throws NCDSurveyNotFoundException{
		
		NCDSurvey NCDSurvey = service.updateNCDSurvey(id, updatedNCDSurvey);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<NCDSurvey> apiResponse = ApiResponse.<NCDSurvey>builder()
				.status(HttpStatus.OK.toString())
				.message("NCDSurvey Details with id "+ id)
				.payload(NCDSurvey)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<NCDSurvey>> udpateNCDSurvey(@PathVariable UUID id) throws NCDSurveyNotFoundException{
		
		service.deleteNCDSurvey(id);
//		UserDTO dto = mapper.map(user, UserDTO.class);
		
		ApiResponse<NCDSurvey> apiResponse = ApiResponse.<NCDSurvey>builder()
				.status(HttpStatus.OK.toString())
				.message("NCDSurvey Deleted with id "+ id)
				.build();
		
		return ResponseEntity.ok(apiResponse);
	}
}
