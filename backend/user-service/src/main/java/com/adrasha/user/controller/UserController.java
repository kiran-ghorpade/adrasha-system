package com.adrasha.user.controller;

import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrasha.core.dto.ErrorResponse;
import com.adrasha.core.dto.ExampleMatcherUtils;
import com.adrasha.core.dto.ValidationErrorResponse;
import com.adrasha.core.dto.filter.UserFilterDTO;
import com.adrasha.core.dto.page.UserPageResponseDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.model.Role;
import com.adrasha.user.dto.user.UserUpdateDTO;
import com.adrasha.user.model.User;
import com.adrasha.user.service.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "User")
@ApiResponses({
	@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),	
	@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
public class UserController {
	
		private final UserService userService;
		private final ModelMapper mapper;

		@GetMapping
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserPageResponseDTO.class)))
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
		public Page<UserResponseDTO> getAllUsers(
				UserFilterDTO filterDTO,
			    @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
				Pageable pageable
				){
			
			User searchTerms = mapper.map(filterDTO, User.class);
			
			Example<User> example = Example.of(searchTerms, ExampleMatcherUtils.getDefaultMatcher());
	
			Page<User> usersPage = userService.getUserPage(example, pageable);
			
			Page<UserResponseDTO> dtoPage = usersPage.map(roleRequest -> mapper.map(roleRequest, UserResponseDTO.class));
			
			return dtoPage;
		}
		
		@GetMapping("/count")
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Map.class)))
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
		private long getCount(UserFilterDTO filterDTO) {
			Example<User> user = Example.of(mapper.map(filterDTO, User.class), ExampleMatcherUtils.getDefaultMatcher());
			return userService.getUserCount(user);
		}
		

		@GetMapping("/{id}")
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))	
		@PreAuthorize("hasAnyRole('USER','ADMIN','SYSTEM')")
		public UserResponseDTO getUser(@PathVariable UUID id){
			
			User user = userService.getUser(id);
			
			return mapper.map(user, UserResponseDTO.class);
		}
	
		@PutMapping("/{id}")
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		@PreAuthorize("#id.toString() == authentication.principal.toString()")
		public UserResponseDTO udpatedUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO updatedUser){
			
			User user = mapper.map(updatedUser, User.class);
			user = userService.updateUser(id, user);
			return mapper.map(user, UserResponseDTO.class);
			
		}

	    @DeleteMapping("/{id}/roles/{role}")
		@ApiResponse(responseCode = "204", content = @Content())
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	    @PreAuthorize("#id.toString() == authentication.principal.toString()")
	    public ResponseEntity<Void> removeRole(@PathVariable UUID id, @PathVariable Role role) {
	        userService.removeRoleFromUser(id, role);
	        return ResponseEntity.noContent().build();
	    }

		@DeleteMapping("/{id}")
		@ApiResponse(responseCode = "204", content = @Content())
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
			
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		}

}
